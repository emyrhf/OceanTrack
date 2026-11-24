package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.AreaProtecaoMarinhaDAO;
import dao.AreaProtecaoMarinhaDAOImpl;
import entity.AreaProtecaoMarinha;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AreaProtecaoMarinhaController {

    private AreaProtecaoMarinhaDAO dao = new AreaProtecaoMarinhaDAOImpl();

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<AreaProtecaoMarinha> listagem = FXCollections.observableArrayList();
    private long proximoId = 1;

    private StringProperty idString = new SimpleStringProperty("");
    private StringProperty nome = new SimpleStringProperty(""); // obrigatorio
    private StringProperty estado = new SimpleStringProperty(""); // obrigatorio
    private StringProperty areaKm2String = new SimpleStringProperty(""); // obrigatorio
    private StringProperty orgaoResponsavel = new SimpleStringProperty(""); // obrigatorio
    private StringProperty ativaString = new SimpleStringProperty(""); // obrigatorio ("Ativa" / "Inativa")
    private StringProperty dataCString = new SimpleStringProperty(LocalDate.now().format(formatador)); // automatico

    private final StringProperty mensagemErro = new SimpleStringProperty("");

    public AreaProtecaoMarinhaController() {
        carregarListagem();
        limparCampos();
    }

    public StringProperty idProperty() {
        return this.idString;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public StringProperty estadoProperty() {
        return this.estado;
    }

    public StringProperty areaKm2StringProperty() {
        return this.areaKm2String;
    }

    public StringProperty orgaoResponsavelProperty() {
        return this.orgaoResponsavel;
    }

    public StringProperty ativaStringProperty() {
        return this.ativaString;
    }

    public StringProperty dataCStringProperty() {
        return this.dataCString;
    }

    public StringProperty mensagemErroProperty() {
        return mensagemErro;
    }

    public boolean cadastrar() {
        if (!validarCamposObrigatorios()) {
            return false;
        }

        int areaKm2;
        try {
            areaKm2 = Integer.parseInt(areaKm2String.get().trim());
            if (areaKm2 <= 0) {
                mensagemErro.set("• Área (km²): informe um valor inteiro maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Área (km²): digite apenas números inteiros.");
            return false;
        }

        try {
            StringBuilder sb = new StringBuilder();
            for (String p : nome.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String nomeFormatado = sb.toString();

            sb.setLength(0);
            for (String p : estado.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String estadoFormatado = sb.toString();

            sb.setLength(0);
            for (String p : orgaoResponsavel.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String orgaoFormatado = sb.toString();

            boolean isAtiva = "Ativa".equalsIgnoreCase(ativaString.get().trim());

            AreaProtecaoMarinha area = new AreaProtecaoMarinha();
            area.setId(proximoId++);
            area.setNome(nomeFormatado);
            area.setEstado(estadoFormatado);
            area.setAreaKm2(areaKm2);
            area.setOrgaoResponavel(orgaoFormatado);
            area.setAtiva(isAtiva);
            area.setDataCadastro(LocalDate.now());

            AreaProtecaoMarinha salva = dao.salvar(area);

            if (salva == null) {
                mensagemErro.set("Erro ao salvar a área de proteção no banco de dados.");
                return false;
            }

            salva.setNome(nomeFormatado);
            salva.setEstado(estadoFormatado);
            salva.setOrgaoResponavel(orgaoFormatado);
            salva.setAtiva(isAtiva);

            listagem.add(salva);
            limparCampos();
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao cadastrar área de proteção: " + ex.getMessage());
            return false;
        }
    }

    public boolean deletar() {
        String idTxt = idString.get() == null ? "" : idString.get().trim();
        if (idTxt.isBlank()) {
            mensagemErro.set("Informe ou selecione uma área de proteção para excluir.");
            return false;
        }

        long idAlvo;
        try {
            idAlvo = Long.parseLong(idTxt);
        } catch (NumberFormatException ex) {
            mensagemErro.set("Digite um número válido para excluir.");
            return false;
        }

        AreaProtecaoMarinha alvo = null;
        for (AreaProtecaoMarinha item : dao.pesquisarTodos()) {
            if (item.getId() == idAlvo) {
                alvo = item;
                break;
            }
        }

        if (alvo == null) {
            mensagemErro.set("Nenhuma área de proteção encontrada com o ID " + idAlvo + " para excluir.");
            return false;
        }

        try {
            dao.remover(alvo);
            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao excluir área de proteção: " + ex.getMessage());
            return false;
        }
    }

    public boolean pesquisar() {
        String idTexto = idString.get() == null ? "" : idString.get().trim();
        String nomeTexto = nome.get() == null ? "" : nome.get().trim();
        String estadoTexto = estado.get() == null ? "" : estado.get().trim();
        String orgaoTexto = orgaoResponsavel.get() == null ? "" : orgaoResponsavel.get().trim();
        String ativaTexto = ativaString.get() == null ? "" : ativaString.get().trim();

        if (idTexto.isBlank() && nomeTexto.isBlank() && estadoTexto.isBlank()
                && orgaoTexto.isBlank() && ativaTexto.isBlank()) {
            mensagemErro.set("Informe ao menos um critério para pesquisar.");
            return false;
        }

        List<AreaProtecaoMarinha> todas;
        try {
            todas = dao.pesquisarTodos();
        } catch (Exception ex) {
            mensagemErro.set("Erro ao consultar o banco de dados: " + ex.getMessage());
            return false;
        }

        List<AreaProtecaoMarinha> resultados = new ArrayList<>();

        if (!nomeTexto.isBlank()) {
            String termo = nomeTexto.toLowerCase();
            for (AreaProtecaoMarinha item : todas) {
                if (item.getNome() != null && item.getNome().toLowerCase().contains(termo)) {
                    resultados.add(item);
                }
            }
            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma área encontrada contendo \"" + nomeTexto + "\".");
                return false;
            }
        } else if (!estadoTexto.isBlank()) {
            String termo = estadoTexto.toLowerCase();
            for (AreaProtecaoMarinha item : todas) {
                if (item.getEstado() != null && item.getEstado().toLowerCase().contains(termo)) {
                    resultados.add(item);
                }
            }
            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma área encontrada no estado contendo \"" + estadoTexto + "\".");
                return false;
            }
        } else if (!orgaoTexto.isBlank()) {
            String termo = orgaoTexto.toLowerCase();
            for (AreaProtecaoMarinha item : todas) {
                if (item.getOrgaoResponavel() != null && item.getOrgaoResponavel().toLowerCase().contains(termo)) {
                    resultados.add(item);
                }
            }
            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma área encontrada com o órgão contendo \"" + orgaoTexto + "\".");
                return false;
            }
        } else if (!ativaTexto.isBlank()) {
            boolean filtroAtiva = "Ativa".equalsIgnoreCase(ativaTexto);
            for (AreaProtecaoMarinha item : todas) {
                if (item.isAtiva() == filtroAtiva) {
                    resultados.add(item);
                }
            }
            if (resultados.isEmpty()) {
                mensagemErro.set("Nenhuma área encontrada com o status \"" + ativaTexto + "\".");
                return false;
            }
        } else if (!idTexto.isBlank() && !idTexto.equals("0")) {
            try {
                long idBusca = Long.parseLong(idTexto);
                for (AreaProtecaoMarinha item : todas) {
                    if (item.getId() == idBusca) {
                        resultados.add(item);
                    }
                }
                if (resultados.isEmpty()) {
                    mensagemErro.set("Nenhuma área encontrada com o ID " + idBusca + ".");
                    return false;
                }
            } catch (NumberFormatException ex) {
                mensagemErro.set("Digite um número válido para pesquisar por ID.");
                return false;
            }
        } else {
            mensagemErro.set("Informe ao menos um critério para pesquisar.");
            return false;
        }

        listagem.setAll(resultados);
        mensagemErro.set("");
        return true;
    }

    public boolean atualizar() {
        String idTxt = idString.get() == null ? "" : idString.get().trim();
        if (idTxt.isBlank()) {
            mensagemErro.set("Selecione ou pesquise uma área de proteção antes de atualizar.");
            return false;
        }

        long idAlvo;
        try {
            idAlvo = Long.parseLong(idTxt);
        } catch (NumberFormatException ex) {
            mensagemErro.set("Identificador inválido para atualização.");
            return false;
        }

        if (!validarCamposObrigatorios()) {
            return false;
        }

        int areaKm2;
        try {
            areaKm2 = Integer.parseInt(areaKm2String.get().trim());
            if (areaKm2 <= 0) {
                mensagemErro.set("• Área (km²): informe um valor inteiro maior que zero.");
                return false;
            }
        } catch (NumberFormatException ex) {
            mensagemErro.set("• Área (km²): digite apenas números inteiros.");
            return false;
        }

        AreaProtecaoMarinha existente = null;
        for (AreaProtecaoMarinha item : dao.pesquisarTodos()) {
            if (item.getId() == idAlvo) {
                existente = item;
                break;
            }
        }

        if (existente == null) {
            mensagemErro.set("Área de proteção com ID " + idAlvo + " não encontrada no banco.");
            return false;
        }

        try {
            StringBuilder sb = new StringBuilder();
            for (String p : nome.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String nomeFormatado = sb.toString();

            sb.setLength(0);
            for (String p : estado.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String estadoFormatado = sb.toString();

            sb.setLength(0);
            for (String p : orgaoResponsavel.get().trim().toLowerCase().split("\\s+")) {
                if (!p.isBlank()) {
                    sb.append(sb.length() > 0 ? " " : "")
                            .append(Character.toUpperCase(p.charAt(0)))
                            .append(p.substring(1));
                }
            }
            String orgaoFormatado = sb.toString();

            boolean isAtiva = "Ativa".equalsIgnoreCase(ativaString.get().trim());

            AreaProtecaoMarinha atualizada = new AreaProtecaoMarinha();
            atualizada.setId(idAlvo);
            atualizada.setNome(nomeFormatado);
            atualizada.setEstado(estadoFormatado);
            atualizada.setAreaKm2(areaKm2);
            atualizada.setOrgaoResponavel(orgaoFormatado);
            atualizada.setAtiva(isAtiva);
            atualizada.setDataCadastro(
                    existente.getDataCadastro() != null ? existente.getDataCadastro() : LocalDate.now());

            dao.atualizar(idAlvo, atualizada);

            carregarListagem();
            limparCampos();
            mensagemErro.set("");
            return true;
        } catch (Exception ex) {
            mensagemErro.set("Erro ao atualizar área de proteção: " + ex.getMessage());
            return false;
        }
    }

    public void limparCampos() {
        String dataHoje = LocalDate.now().format(formatador);
        idString.set("");
        nome.set("");
        estado.set("");
        areaKm2String.set("");
        orgaoResponsavel.set("");
        ativaString.set("");
        dataCString.set(dataHoje);
    }

    public void carregarListagem() {
        listagem.setAll(dao.pesquisarTodos());
    }

    private boolean validarCamposObrigatorios() {
        List<String> erros = new ArrayList<>();

        if (nome.get() == null || nome.get().isBlank()) {
            erros.add("• Nome: campo obrigatório.");
        }
        if (estado.get() == null || estado.get().isBlank()) {
            erros.add("• Estado: campo obrigatório.");
        }
        if (areaKm2String.get() == null || areaKm2String.get().isBlank()) {
            erros.add("• Área (km²): campo obrigatório.");
        }
        if (orgaoResponsavel.get() == null || orgaoResponsavel.get().isBlank()) {
            erros.add("• Órgão Responsável: campo obrigatório.");
        }
        if (ativaString.get() == null || ativaString.get().isBlank()) {
            erros.add("• Situação (Ativa/Inativa): campo obrigatório.");
        }

        if (!erros.isEmpty()) {
            mensagemErro.set(String.join("\n", erros));
            return false;
        }

        mensagemErro.set("");
        return true;
    }

    public ObservableList<AreaProtecaoMarinha> getListagem() {
        return listagem;
    }
}