package dao;

import java.util.List;
import entity.OcorrenciaPoluicao;

public interface OcorrenciaPoluicaoDAO {
    OcorrenciaPoluicao salvar(OcorrenciaPoluicao ocorrencia);
    List<OcorrenciaPoluicao> pesquisarTodos();
    void atualizar(long id, OcorrenciaPoluicao ocorrencia);
    void remover(OcorrenciaPoluicao ocorrencia);
}
