package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entity.EspecieMarinha;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList; 

public class EspecieMarinhaController {
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


    // função CREATE que valida campos obrigatórios e os aceita campos opcionais. 
    public boolean cadastrar() {
        if (nomeComum.get() == null || habitat.get() == null || status.get() == null ) {
            // validação de campos obrigatórios não preenchidos. se estao vazios -> retorna falso e o objeto não é criado
            return false;
        }else{
            try {
                EspecieMarinha e = new EspecieMarinha();
                e.setId(proximoId++); 
                e.setNomeComum(nomeComum.get());
                e.setHabitat(habitat.get());
                e.setStatusConservacao(status.get());

                e.setNomeCientifico(nomeCientifico.get() == null || nomeCientifico.get().isBlank() ? "" : nomeCientifico.get());

                LocalDate cadastro = LocalDate.now();
                e.setDataCadastro(cadastro);
                e.setDataUltimaAtualizacao(cadastro);

                listagem.add(e);
                limparCampos();
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    //funcao READ com listagem de objetos cadastrados (EM MEMORIA)
    public ObservableList<EspecieMarinha> getTableData() {
        return listagem;
    }

    //funcao UPDATE que atualiza os campos de um objeto existente
    public boolean atualizar(EspecieMarinha e) {
        if (e == null) {
            return false;
        }else{
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

    public ObservableList<EspecieMarinha> getListagem(){
        return listagem;
    }
}
