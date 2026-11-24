package control;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import dao.OcorrenciaPoluicaoDAO;
import dao.OcorrenciaPoluicaoDAOImpl;
import entity.OcorrenciaPoluicao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OcorrenciaPoluicaoController {

    private OcorrenciaPoluicaoDAO dao = new OcorrenciaPoluicaoDAOImpl();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<OcorrenciaPoluicao> listagem = FXCollections.observableArrayList();

    private StringProperty idString = new SimpleStringProperty("");
    private StringProperty tipo = new SimpleStringProperty("");
    private StringProperty local = new SimpleStringProperty("");
    private StringProperty dataString = new SimpleStringProperty(LocalDate.now().format(formatador));
    private StringProperty gravidade = new SimpleStringProperty("");
    private StringProperty volumeEstimadoString = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");
    private final StringProperty mensagemErro = new SimpleStringProperty("");

    public OcorrenciaPoluicaoController() {
        carregarListagem();
        limparCampos();
    }

    public StringProperty idProperty() { return idString; }
    public StringProperty tipoProperty() { return tipo; }
    public StringProperty localProperty() { return local; }
    public StringProperty dataStringProperty() { return dataString; }
    public StringProperty gravidadeProperty() { return gravidade; }
    public StringProperty volumeEstimadoStringProperty() { return volumeEstimadoString; }
    public StringProperty descricaoProperty() { return descricao; }
    public StringProperty statusProperty() { return status; }
    public StringProperty mensagemErroProperty() { return mensagemErro; }

    public boolean cadastrar() {
        if (!validarCamposObrigatorios()) {
            return false;
        }

        LocalDate data;
        BigDecimal volume;
        try {
            data = LocalDate.parse(dataString.get().trim(), formatador);
        } catch (DateTimeParseException ex) {
            mensagemErro.set("• Data: informe uma data válida no formato dd/MM/aaaa.");
            return false;
        }

        try {
            volume = new BigDecimal(volumeEstimadoString.get().trim().replace(",", "."));
            if (volume.compareTo(BigDecimal.ZERO) <= 0) {
                mensagemErro.set("• Volume estimado: informe um valor maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Volume estimado: digite um número válido (ex.: 12,5).");
            return false;
        }

        try {
            OcorrenciaPoluicao ocorrencia = new OcorrenciaPoluicao();
            ocorrencia.setTipo(tipo.get().trim());
            ocorrencia.setLocal(capitalizar(local.get()));
            ocorrencia.setData(data);
            ocorrencia.setGravidade(gravidade.get().trim());
            ocorrencia.setVolumeEstimado(volume);
            ocorrencia.setDescricao(descricao.get().trim());
            ocorrencia.setStatus(status.get().trim());

            OcorrenciaPoluicao salva = dao.salvar(ocorrencia);
            if (salva == null) {
                mensagemErro.set("Erro ao salvar a ocorrência no banco de dados.");
                return false;
            }

            listagem.add(salva);
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao cadastrar ocorrência de poluição: " + ex.getMessage());
            return false;
        }
    }

    public boolean pesquisar() {
        String idTexto = texto(idString.get());
        String tipoTexto = texto(tipo.get());
        String localTexto = texto(local.get());
        String dataTexto = texto(dataString.get());
        String gravidadeTexto = texto(gravidade.get());
        String statusTexto = texto(status.get());

        boolean semFiltros = idTexto.isBlank() && tipoTexto.isBlank() && localTexto.isBlank()
                && gravidadeTexto.isBlank() && statusTexto.isBlank()
                && (dataTexto.isBlank() || dataTexto.equals(LocalDate.now().format(formatador)));
        if (semFiltros) {
            mensagemErro.set("Informe um critério para pesquisar.");
            return false;
        }

        LocalDate dataBusca = null;
        if (!dataTexto.isBlank() && !dataTexto.equals(LocalDate.now().format(formatador))) {
            try {
                dataBusca = LocalDate.parse(dataTexto, formatador);
            } catch (DateTimeParseException ex) {
                mensagemErro.set("Informe uma data válida no formato dd/MM/aaaa.");
                return false;
            }
        }

        try {
            List<OcorrenciaPoluicao> resultados = new ArrayList<>();
            for (OcorrenciaPoluicao item : dao.pesquisarTodos()) {
                boolean corresponde = true;
                if (!idTexto.isBlank()) {
                    try {
                        corresponde &= item.getId() == Long.parseLong(idTexto);
                    } catch (NumberFormatException ex) {
                        mensagemErro.set("Digite um ID numérico válido.");
                        return false;
                    }
                }
                if (!tipoTexto.isBlank()) {
                    corresponde &= contem(item.getTipo(), tipoTexto);
                }
                if (!localTexto.isBlank()) {
                    corresponde &= contem(item.getLocal(), localTexto);
                }
                if (!gravidadeTexto.isBlank()) {
                    corresponde &= contem(item.getGravidade(), gravidadeTexto);
                }
                if (!statusTexto.isBlank()) {
                    corresponde &= contem(item.getStatus(), statusTexto);
                }
                if (dataBusca != null) {
                    corresponde &= dataBusca.equals(item.getData());
                }
                if (corresponde) {
                    resultados.add(item);
                }
            }

            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma ocorrência encontrada com os critérios informados.");
                return false;
            }
            listagem.setAll(resultados);
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao consultar ocorrências: " + ex.getMessage());
            return false;
        }
    }

    public boolean atualizar() {
        String idTexto = texto(idString.get());
        if (idTexto.isBlank()) {
            mensagemErro.set("Selecione uma ocorrência antes de atualizar.");
            return false;
        }
        long id;
        try {
            id = Long.parseLong(idTexto);
        } catch (NumberFormatException ex) {
            mensagemErro.set("Identificador inválido para atualização.");
            return false;
        }
        if (!validarCamposObrigatorios()) {
            return false;
        }

        LocalDate data;
        BigDecimal volume;
        try {
            data = LocalDate.parse(dataString.get().trim(), formatador);
        } catch (DateTimeParseException ex) {
            mensagemErro.set("• Data: informe uma data válida no formato dd/MM/aaaa.");
            return false;
        }
        try {
            volume = new BigDecimal(volumeEstimadoString.get().trim().replace(",", "."));
            if (volume.compareTo(BigDecimal.ZERO) <= 0) {
                mensagemErro.set("• Volume estimado: informe um valor maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Volume estimado: digite um número válido (ex.: 12,5).");
            return false;
        }

        try {
            OcorrenciaPoluicao atualizada = new OcorrenciaPoluicao();
            atualizada.setId(id);
            atualizada.setTipo(tipo.get().trim());
            atualizada.setLocal(capitalizar(local.get()));
            atualizada.setData(data);
            atualizada.setGravidade(gravidade.get().trim());
            atualizada.setVolumeEstimado(volume);
            atualizada.setDescricao(descricao.get().trim());
            atualizada.setStatus(status.get().trim());

            dao.atualizar(id, atualizada);
            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao atualizar ocorrência: " + ex.getMessage());
            return false;
        }
    }

    public boolean deletar() {
        String idTexto = texto(idString.get());
        if (idTexto.isBlank()) {
            mensagemErro.set("Informe ou selecione uma ocorrência para excluir.");
            return false;
        }

        long id;
        try {
            id = Long.parseLong(idTexto);
        } catch (NumberFormatException ex) {
            mensagemErro.set("Digite um ID numérico válido para excluir.");
            return false;
        }

        try {
            OcorrenciaPoluicao alvo = null;
            for (OcorrenciaPoluicao item : dao.pesquisarTodos()) {
                if (item.getId() == id) {
                    alvo = item;
                    break;
                }
            }
            if (alvo == null) {
                mensagemErro.set("Nenhuma ocorrência encontrada com o ID " + id + ".");
                return false;
            }

            dao.remover(alvo);
            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao excluir ocorrência: " + ex.getMessage());
            return false;
        }
    }

    public void limparCampos() {
        idString.set("");
        tipo.set("");
        local.set("");
        dataString.set(LocalDate.now().format(formatador));
        gravidade.set("");
        volumeEstimadoString.set("");
        descricao.set("");
        status.set("");
    }

    public void carregarListagem() {
        listagem.setAll(dao.pesquisarTodos());
    }

    private boolean validarCamposObrigatorios() {
        List<String> erros = new ArrayList<>();
        if (texto(tipo.get()).isBlank()) erros.add("• Tipo: campo obrigatório.");
        if (texto(local.get()).isBlank()) erros.add("• Local: campo obrigatório.");
        if (texto(dataString.get()).isBlank()) erros.add("• Data: campo obrigatório.");
        if (texto(gravidade.get()).isBlank()) erros.add("• Gravidade: campo obrigatório.");
        if (texto(volumeEstimadoString.get()).isBlank()) erros.add("• Volume estimado: campo obrigatório.");
        if (texto(descricao.get()).isBlank()) erros.add("• Descrição: campo obrigatório.");
        if (texto(status.get()).isBlank()) erros.add("• Status: campo obrigatório.");

        if (!erros.isEmpty()) {
            mensagemErro.set(String.join("\n", erros));
            return false;
        }
        mensagemErro.set("");
        return true;
    }

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private boolean contem(String valor, String termo) {
        return valor != null && valor.toLowerCase().contains(termo.toLowerCase());
    }

    private String capitalizar(String valor) {
        StringBuilder sb = new StringBuilder();
        for (String palavra : texto(valor).toLowerCase().split("\\s+")) {
            if (!palavra.isBlank()) {
                sb.append(sb.length() > 0 ? " " : "")
                        .append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1));
            }
        }
        return sb.toString();
    }

    public ObservableList<OcorrenciaPoluicao> getListagem() {
        return listagem;
    }
}
