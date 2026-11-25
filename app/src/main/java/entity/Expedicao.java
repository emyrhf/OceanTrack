package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expedicao {
    private long id;
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String localizacao;
    private String embarcacao;
    private BigDecimal orcamento;
    private String objetivo;
    private String status; // PLANEJADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA

    public Expedicao() {
        this(0, "", LocalDate.now(), null, "", "", BigDecimal.ZERO, "", "PLANEJADA");
    }

    public Expedicao(long id, String titulo, LocalDate dataInicio, LocalDate dataFim,
                     String localizacao, String embarcacao, BigDecimal orcamento,
                     String objetivo, String status) {
        this.id = id;
        this.titulo = titulo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.localizacao = localizacao;
        this.embarcacao = embarcacao;
        this.orcamento = orcamento;
        this.objetivo = objetivo;
        this.status = status;
    }

    // Getters:
    public long getId() { return this.id; }
    public String getTitulo() { return this.titulo; }
    public LocalDate getDataInicio() { return this.dataInicio; }
    public LocalDate getDataFim() { return this.dataFim; }
    public String getLocalizacao() { return this.localizacao; }
    public String getEmbarcacao() { return this.embarcacao; }
    public BigDecimal getOrcamento() { return this.orcamento; }
    public String getObjetivo() { return this.objetivo; }
    public String getStatus() { return this.status; }

    // Setters:
    public void setId(long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public void setEmbarcacao(String embarcacao) { this.embarcacao = embarcacao; }
    public void setOrcamento(BigDecimal orcamento) { this.orcamento = orcamento; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public void setStatus(String status) { this.status = status; }
}