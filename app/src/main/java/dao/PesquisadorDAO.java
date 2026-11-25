package dao;

import entity.Pesquisador;
import java.util.List;

public interface PesquisadorDAO {
    Pesquisador salvar(Pesquisador pesquisador);
    void atualizar(long id, Pesquisador pesquisador);
    void remover(Pesquisador pesquisador);
    List<Pesquisador> pesquisarTodos();
} 