package control;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import dao.AcaoLimpezaDAO;
import dao.AcaoLimpezaDAOImpl;
import entity.AcaoLimpeza;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcaoLimpezaController {

    private AcaoLimpezaDAO dao = new AcaoLimpezaDAOImpl();
    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<AcaoLimpeza> listagem = FXCollections.observableArrayList();

    private StringProperty idString = new SimpleStringProperty("");
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty dataString = new SimpleStringProperty(LocalDate.now().format(formatador));
    private StringProperty local = new SimpleStringProperty("");
    private StringProperty numeroParticipantesString = new SimpleStringProperty("");
    private StringProperty quantidadeLixoKgString = new SimpleStringProperty("");
    private StringProperty organizador = new SimpleStringProperty("");
    private StringProperty tipoResiduoPredominante = new SimpleStringProperty("");
    private final StringProperty mensagemErro = new SimpleStringProperty("");

    public AcaoLimpezaController() {
        carregarListagem();
        limparCampos();
    }

    public StringProperty idProperty() { return idString; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty dataStringProperty() { return dataString; }
    public StringProperty localProperty() { return local; }
    public StringProperty numeroParticipantesStringProperty() { return numeroParticipantesString; }
    public StringProperty quantidadeLixoKgStringProperty() { return quantidadeLixoKgString; }
    public StringProperty organizadorProperty() { return organizador; }
    public StringProperty tipoResiduoPredominanteProperty() { return tipoResiduoPredominante; }
    public StringProperty mensagemErroProperty() { return mensagemErro; }

    public boolean cadastrar() {
        if (!validarCamposObrigatorios()) {
            return false;
        }

        LocalDate data;
        int participantes;
        BigDecimal quantidade;
        try {
            data = LocalDate.parse(dataString.get().trim(), formatador);
        } catch (DateTimeParseException ex) {
            mensagemErro.set("• Data: informe uma data válida no formato dd/MM/aaaa.");
            return false;
        }
        try {
            participantes = Integer.parseInt(numeroParticipantesString.get().trim());
            if (participantes <= 0) {
                mensagemErro.set("• Nº de participantes: informe um número maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Nº de participantes: digite um número inteiro válido.");
            return false;
        }
        try {
            quantidade = new BigDecimal(quantidadeLixoKgString.get().trim().replace(",", "."));
            if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
                mensagemErro.set("• Quantidade de lixo: informe um valor maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Quantidade de lixo: digite um número válido (ex.: 25,5).");
            return false;
        }

        try {
            AcaoLimpeza acao = new AcaoLimpeza();
            acao.setNome(capitalizar(nome.get()));
            acao.setData(data);
            acao.setLocal(capitalizar(local.get()));
            acao.setNumeroParticipantes(participantes);
            acao.setQuantidadeLixoKg(quantidade);
            acao.setOrganizador(capitalizar(organizador.get()));
            acao.setTipoResiduoPredominante(tipoResiduoPredominante.get().trim());

            AcaoLimpeza salva = dao.salvar(acao);
            if (salva == null) {
                mensagemErro.set("Erro ao salvar a ação de limpeza no banco de dados.");
                return false;
            }

            listagem.add(salva);
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao cadastrar ação de limpeza: " + ex.getMessage());
            return false;
        }
    }

    public boolean pesquisar() {
        String idTexto = texto(idString.get());
        String nomeTexto = texto(nome.get());
        String dataTexto = texto(dataString.get());
        String localTexto = texto(local.get());
        String organizadorTexto = texto(organizador.get());
        String residuoTexto = texto(tipoResiduoPredominante.get());

        boolean semFiltros = idTexto.isBlank() && nomeTexto.isBlank() && localTexto.isBlank()
                && organizadorTexto.isBlank() && residuoTexto.isBlank()
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
            List<AcaoLimpeza> resultados = new ArrayList<>();
            for (AcaoLimpeza item : dao.pesquisarTodos()) {
                boolean corresponde = true;
                if (!idTexto.isBlank()) {
                    try {
                        corresponde &= item.getId() == Long.parseLong(idTexto);
                    } catch (NumberFormatException ex) {
                        mensagemErro.set("Digite um ID numérico válido.");
                        return false;
                    }
                }
                if (!nomeTexto.isBlank()) corresponde &= contem(item.getNome(), nomeTexto);
                if (!localTexto.isBlank()) corresponde &= contem(item.getLocal(), localTexto);
                if (!organizadorTexto.isBlank()) corresponde &= contem(item.getOrganizador(), organizadorTexto);
                if (!residuoTexto.isBlank()) corresponde &= contem(item.getTipoResiduoPredominante(), residuoTexto);
                if (dataBusca != null) corresponde &= dataBusca.equals(item.getData());
                if (corresponde) resultados.add(item);
            }

            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma ação de limpeza encontrada com os critérios informados.");
                return false;
            }
            listagem.setAll(resultados);
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao consultar ações de limpeza: " + ex.getMessage());
            return false;
        }
    }

    public boolean atualizar() {
        String idTexto = texto(idString.get());
        if (idTexto.isBlank()) {
            mensagemErro.set("Selecione uma ação de limpeza antes de atualizar.");
            return false;
        }
        long id;
        try {
            id = Long.parseLong(idTexto);
        } catch (NumberFormatException ex) {
            mensagemErro.set("Identificador inválido para atualização.");
            return false;
        }
        if (!validarCamposObrigatorios()) return false;

        LocalDate data;
        int participantes;
        BigDecimal quantidade;
        try {
            data = LocalDate.parse(dataString.get().trim(), formatador);
        } catch (DateTimeParseException ex) {
            mensagemErro.set("• Data: informe uma data válida no formato dd/MM/aaaa.");
            return false;
        }
        try {
            participantes = Integer.parseInt(numeroParticipantesString.get().trim());
            if (participantes <= 0) {
                mensagemErro.set("• Nº de participantes: informe um número maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Nº de participantes: digite um número inteiro válido.");
            return false;
        }
        try {
            quantidade = new BigDecimal(quantidadeLixoKgString.get().trim().replace(",", "."));
            if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
                mensagemErro.set("• Quantidade de lixo: informe um valor maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Quantidade de lixo: digite um número válido (ex.: 25,5).");
            return false;
        }

        try {
            AcaoLimpeza atualizada = new AcaoLimpeza();
            atualizada.setId(id);
            atualizada.setNome(capitalizar(nome.get()));
            atualizada.setData(data);
            atualizada.setLocal(capitalizar(local.get()));
            atualizada.setNumeroParticipantes(participantes);
            atualizada.setQuantidadeLixoKg(quantidade);
            atualizada.setOrganizador(capitalizar(organizador.get()));
            atualizada.setTipoResiduoPredominante(tipoResiduoPredominante.get().trim());

            dao.atualizar(id, atualizada);
            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao atualizar ação de limpeza: " + ex.getMessage());
            return false;
        }
    }

    public boolean deletar() {
        String idTexto = texto(idString.get());
        if (idTexto.isBlank()) {
            mensagemErro.set("Informe ou selecione uma ação de limpeza para excluir.");
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
            AcaoLimpeza alvo = null;
            for (AcaoLimpeza item : dao.pesquisarTodos()) {
                if (item.getId() == id) {
                    alvo = item;
                    break;
                }
            }
            if (alvo == null) {
                mensagemErro.set("Nenhuma ação de limpeza encontrada com o ID " + id + ".");
                return false;
            }

            dao.remover(alvo);
            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao excluir ação de limpeza: " + ex.getMessage());
            return false;
        }
    }

    public void limparCampos() {
        idString.set("");
        nome.set("");
        dataString.set(LocalDate.now().format(formatador));
        local.set("");
        numeroParticipantesString.set("");
        quantidadeLixoKgString.set("");
        organizador.set("");
        tipoResiduoPredominante.set("");
    }

    public void carregarListagem() {
        listagem.setAll(dao.pesquisarTodos());
    }

    private boolean validarCamposObrigatorios() {
        List<String> erros = new ArrayList<>();
        if (texto(nome.get()).isBlank()) erros.add("• Nome: campo obrigatório.");
        if (texto(dataString.get()).isBlank()) erros.add("• Data: campo obrigatório.");
        if (texto(local.get()).isBlank()) erros.add("• Local: campo obrigatório.");
        if (texto(numeroParticipantesString.get()).isBlank()) erros.add("• Nº de participantes: campo obrigatório.");
        if (texto(quantidadeLixoKgString.get()).isBlank()) erros.add("• Quantidade de lixo coletada: campo obrigatório.");
        if (texto(organizador.get()).isBlank()) erros.add("• Organizador: campo obrigatório.");
        if (texto(tipoResiduoPredominante.get()).isBlank()) erros.add("• Tipo de resíduo predominante: campo obrigatório.");

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

    public ObservableList<AcaoLimpeza> getListagem() {
        return listagem;
    }
}
