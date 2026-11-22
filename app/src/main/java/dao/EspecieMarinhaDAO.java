package dao;

import entity.EspecieMarinha;
import java.util.List;

public interface EspecieMarinhaDAO {
    EspecieMarinha salvar(EspecieMarinha especie);
    void atualizar(long id, EspecieMarinha especie);
    void remover(EspecieMarinha especie);
    List<EspecieMarinha> pesquisarTodos();
}