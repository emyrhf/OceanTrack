package dao;
import entity.Expedicao;
import java.util.List;
public interface ExpedicaoDAO {
    
    Expedicao salvar(Expedicao expedicao);
    void atualizar(long id, Expedicao expedicao);
    void remover(Expedicao expedicao);
    List<Expedicao> pesquisarTodos();
} 

