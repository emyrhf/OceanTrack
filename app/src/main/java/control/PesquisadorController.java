package control;

import dao.PesquisadorDAO;
import dao.PesquisadorDAOImpl;
import entity.Pesquisador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PesquisadorController {

    private final PesquisadorDAO dao = new PesquisadorDAOImpl();
    private final ObservableList<Pesquisador> listagem = FXCollections.observableArrayList();

    public PesquisadorController() {
        carregar();
    }

    public ObservableList<Pesquisador> getListagem() {
        return listagem;
    }

    public void carregar() {
        listagem.setAll(dao.pesquisarTodos());
    }

    public void salvar(String nome, String cpf, String telefone,
                       String email, String instituicao, String tipo) {
        validar(nome, cpf, tipo);
        Pesquisador p = new Pesquisador(0, nome.trim(), cpf.trim(), telefone, email, instituicao, tipo);
        dao.salvar(p);
        carregar();
    }

    public void atualizar(Pesquisador selecionado, String nome, String cpf, String telefone,
                          String email, String instituicao, String tipo) {
        if (selecionado == null) {
            throw new IllegalArgumentException("Selecione um pesquisador na tabela.");
        }
        validar(nome, cpf, tipo);
        selecionado.setNome(nome.trim());
        selecionado.setCpf(cpf.trim());
        selecionado.setTelefone(telefone);
        selecionado.setEmail(email);
        selecionado.setInstituicao(instituicao);
        selecionado.setTipo(tipo);
        dao.atualizar(selecionado.getId(), selecionado);
        carregar();
    }

    public void remover(Pesquisador selecionado) {
        if (selecionado == null) {
            throw new IllegalArgumentException("Selecione um pesquisador na tabela.");
        }
        dao.remover(selecionado);
        carregar();
    }

    private void validar(String nome, String cpf, String tipo) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Escolha o tipo (Pesquisador ou Voluntário).");
        }
    }
}
