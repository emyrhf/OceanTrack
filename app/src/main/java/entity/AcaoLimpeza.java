package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AcaoLimpeza {
    private long id;
    private String nome;
    private LocalDate data;
    private String local;
    private int numeroParticipantes;
    private BigDecimal quantidadeLixoKg;
    private String organizador;
    private String tipoResiduoPredominante;

    public AcaoLimpeza() {
    }

    public AcaoLimpeza(long id, String nome, LocalDate data, String local,
            int numeroParticipantes, BigDecimal quantidadeLixoKg,
            String organizador, String tipoResiduoPredominante) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.numeroParticipantes = numeroParticipantes;
        this.quantidadeLixoKg = quantidadeLixoKg;
        this.organizador = organizador;
        this.tipoResiduoPredominante = tipoResiduoPredominante;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public BigDecimal getQuantidadeLixoKg() {
        return quantidadeLixoKg;
    }

    public String getOrganizador() {
        return organizador;
    }

    public String getTipoResiduoPredominante() {
        return tipoResiduoPredominante;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public void setQuantidadeLixoKg(BigDecimal quantidadeLixoKg) {
        this.quantidadeLixoKg = quantidadeLixoKg;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public void setTipoResiduoPredominante(String tipoResiduoPredominante) {
        this.tipoResiduoPredominante = tipoResiduoPredominante;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %d, %s, %s, %s",
                id, nome, data, local, numeroParticipantes,
                quantidadeLixoKg, organizador, tipoResiduoPredominante);
    }
}
