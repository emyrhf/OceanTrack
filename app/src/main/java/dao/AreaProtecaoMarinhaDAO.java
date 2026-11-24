package dao;

import java.util.List;
import entity.AreaProtecaoMarinha;

public interface AreaProtecaoMarinhaDAO {
    AreaProtecaoMarinha salvar(AreaProtecaoMarinha area);
    List<AreaProtecaoMarinha> pesquisarTodos();
    void atualizar(long id, AreaProtecaoMarinha area);
    void remover(AreaProtecaoMarinha area);
}