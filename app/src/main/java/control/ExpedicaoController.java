package control;

import java.math.BigDecimal;
import java.time.LocalDate;

import dao.ExpedicaoDAO;
import dao.ExpedicaoDAOImpl;
import entity.Expedicao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExpedicaoController {

    private final ExpedicaoDAO dao = new ExpedicaoDAOImpl();
    private final ObservableList<Expedicao> listagem = FXCollections.observableArrayList();

    public ExpedicaoController() {
        carregar();
    }

    public ObservableList<Expedicao> getListagem() {
        return listagem;
    }

    public void carregar() {
        listagem.setAll(dao.pesquisarTodos());
    }

    public void salvar(String titulo, LocalDate inicio, LocalDate fim, String localizacao,
                       String embarcacao, String orcamento, String objetivo, String status) {
        BigDecimal valor = validar(titulo, inicio, fim, localizacao, embarcacao, orcamento, status);
        dao.salvar(new Expedicao(0, titulo.trim(), inicio, fim, localizacao.trim(),
                embarcacao.trim(), valor, objetivo, status));
        carregar();
    }

    public void atualizar(Expedicao selecionada, String titulo, LocalDate inicio, LocalDate fim,
                          String localizacao, String embarcacao, String orcamento,
                          String objetivo, String status) {
        if (selecionada == null) {
            throw new IllegalArgumentException("Selecione uma expedição na tabela.");
        }
        BigDecimal valor = validar(titulo, inicio, fim, localizacao, embarcacao, orcamento, status);
        selecionada.setTitulo(titulo.trim());
        selecionada.setDataInicio(inicio);
        selecionada.setDataFim(fim);
        selecionada.setLocalizacao(localizacao.trim());
        selecionada.setEmbarcacao(embarcacao.trim());
        selecionada.setOrcamento(valor);
        selecionada.setObjetivo(objetivo);
        selecionada.setStatus(status);
        dao.atualizar(selecionada.getId(), selecionada);
        carregar();
    }

    public void remover(Expedicao selecionada) {
        if (selecionada == null) {
            throw new IllegalArgumentException("Selecione uma expedição na tabela.");
        }
        dao.remover(selecionada);
        carregar();
    }

    // Valida tudo e devolve o orçamento já convertido para número
    private BigDecimal validar(String titulo, LocalDate inicio, LocalDate fim, String localizacao,
                               String embarcacao, String orcamento, String status) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }
        if (inicio == null) {
            throw new IllegalArgumentException("A data de início é obrigatória.");
        }
        if (fim != null && fim.isBefore(inicio)) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }
        if (localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("O local é obrigatório.");
        }
        if (embarcacao == null || embarcacao.isBlank()) {
            throw new IllegalArgumentException("A embarcação é obrigatória.");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Escolha o status.");
        }
        if (orcamento == null || orcamento.isBlank()) {
            throw new IllegalArgumentException("O orçamento é obrigatório.");
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(orcamento.trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Orçamento inválido. Use números, como 15000,50.");
        }
        if (valor.signum() < 0) {
            throw new IllegalArgumentException("O orçamento não pode ser negativo.");
        }
        return valor;
    }
}