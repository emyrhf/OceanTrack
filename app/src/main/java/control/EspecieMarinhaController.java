package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.EspecieMarinhaDAO;
import dao.EspecieMarinhaDAOImpl;
import entity.EspecieMarinha;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EspecieMarinhaController {

    private EspecieMarinhaDAO dao = new EspecieMarinhaDAOImpl();

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ObservableList<EspecieMarinha> listagem = FXCollections.observableArrayList();
    private long proximoId = 1;

    private StringProperty idString = new SimpleStringProperty(String.valueOf(proximoId));
    private StringProperty nomeComum = new SimpleStringProperty(""); // obrigatorio
    private StringProperty nomeCientifico = new SimpleStringProperty(""); // opcional
    private StringProperty habitat = new SimpleStringProperty(""); // obrigatorio
    private StringProperty status = new SimpleStringProperty(""); // obrigatorio
    private StringProperty dataCString = new SimpleStringProperty(LocalDate.now().format(formatador)); // automatico
    private StringProperty dataAString = new SimpleStringProperty(LocalDate.now().format(formatador)); // automatico

    private final StringProperty mensagemErro = new SimpleStringProperty("");

    public EspecieMarinhaController() {
        carregarListagem();
        limparCampos();
    }

    public StringProperty idProperty() {
        return this.idString;
    }

    public StringProperty nomeComumProperty() {
        return this.nomeComum;
    }

    public StringProperty nomeCientificoProperty() {
        return this.nomeCientifico;
    }

    public StringProperty habitatProperty() {
        return this.habitat;
    }

    public StringProperty statusProperty() {
        return this.status;
    }

    public StringProperty dataCStringProperty() {
        return this.dataCString;
    }

    public StringProperty dataAStringProperty() {
        return this.dataAString;
    }

    public StringProperty mensagemErroProperty() {
        return mensagemErro;
    }

    // função CREATE que valida campos obrigatórios e os aceita campos opcionais.
    // VALIDADO EM BANCO DE DADOS LOCAL!!
    public boolean cadastrar() {
        if (!validarCamposObrigatorios()) {
            // validação de campos obrigatórios não preenchidos. se estao vazios -> retorna
            // falso e o objeto não é criado
            return false;
        } else {
            try {
                EspecieMarinha e = new EspecieMarinha();
                e.setId(proximoId++);
                e.setNomeComum(nomeComum.get());
                e.setHabitat(habitat.get());
                e.setStatusConservacao(status.get());

                e.setNomeCientifico(
                        nomeCientifico.get() == null || nomeCientifico.get().isBlank() ? "" : nomeCientifico.get());

                LocalDate cadastro = LocalDate.now();
                e.setDataCadastro(cadastro);
                e.setDataUltimaAtualizacao(cadastro);

                EspecieMarinha salva = dao.salvar(e);

                if (salva == null) {
                    mensagemErro.set("Erro ao salvar a espécie no banco de dados.");
                    return false;
                }

                listagem.add(salva);
                limparCampos();
                return true;
            } catch (Exception ex) {
                mensagemErro.set("Erro ao cadastrar espécie: " + ex.getMessage());
                return false;
            }
        }
    }

    // funcao READ com listagem de objetos cadastrados (EM MEMORIA)
    // INUTILIZADA: ja tem função que retorna objetos carregados do bd
    // public ObservableList<EspecieMarinha> getTableData() {
    // return listagem;
    // }

    // funcao UPDATE que atualiza os campos de um objeto existente
    public boolean atualizar(EspecieMarinha e) {
        if (e == null) {
            return false;
        } else {
            e.setNomeComum(nomeComum.get());
            e.setNomeCientifico(nomeCientifico.get());
            e.setHabitat(habitat.get());
            e.setStatusConservacao(status.get());

            LocalDate atualizacao = LocalDate.now();
            e.setDataUltimaAtualizacao(atualizacao);

            return true;
        }
    }

    public boolean deletar() {
        return true;
    }

    public boolean pesquisar() {
        return true;
    }

    public boolean atualizar() {
        return true;
    }

    public void limparCampos() {
        String dataHoje = LocalDate.now().format(formatador);
        idString.set(String.valueOf(proximoId));
        nomeComum.set("");
        nomeCientifico.set("");
        habitat.set("");
        status.set("");
        dataCString.set(dataHoje);
        dataAString.set(dataHoje);
    }

    public void carregarListagem() {
        listagem.setAll(dao.pesquisarTodos());
    }

    private boolean validarCamposObrigatorios() {
        List<String> erros = new ArrayList<>();

        if (nomeComum.get() == null || nomeComum.get().isBlank()) {
            erros.add("• Nome Comum: campo obrigatório.");
        }
        if (habitat.get() == null || habitat.get().isBlank()) {
            erros.add("• Habitat: campo obrigatório.");
        }
        if (status.get() == null || status.get().isBlank()) {
            erros.add("• Status de Conservação: campo obrigatório.");
        }

        if (!erros.isEmpty()) {
            mensagemErro.set(String.join("\n", erros));
            return false;
        }

        mensagemErro.set("");
        return true;
    }

    public ObservableList<EspecieMarinha> getListagem() {
        return listagem;
    }
}
