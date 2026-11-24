package dao;

import java.util.List;
import entity.AcaoLimpeza;

public interface AcaoLimpezaDAO {
    AcaoLimpeza salvar(AcaoLimpeza acao);
    List<AcaoLimpeza> pesquisarTodos();
    void atualizar(long id, AcaoLimpeza acao);
    void remover(AcaoLimpeza acao);
}
