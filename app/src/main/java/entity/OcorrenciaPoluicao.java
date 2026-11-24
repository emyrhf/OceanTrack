package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OcorrenciaPoluicao {
    private long id;
    private String tipo;
    private String local;
    private LocalDate data;
    private String gravidade;
    private BigDecimal volumeEstimado;
    private String descricao;
    private String status;

    public OcorrenciaPoluicao() {
    }

    public OcorrenciaPoluicao(long id, String tipo, String local, LocalDate data,
            String gravidade, BigDecimal volumeEstimado, String descricao, String status) {
        this.id = id;
        this.tipo = tipo;
        this.local = local;
        this.data = data;
        this.gravidade = gravidade;
        this.volumeEstimado = volumeEstimado;
        this.descricao = descricao;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLocal() {
        return local;
    }

    public LocalDate getData() {
        return data;
    }

    public String getGravidade() {
        return gravidade;
    }

    public BigDecimal getVolumeEstimado() {
        return volumeEstimado;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public void setVolumeEstimado(BigDecimal volumeEstimado) {
        this.volumeEstimado = volumeEstimado;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s, %s",
                id, tipo, local, data, gravidade, volumeEstimado, descricao, status);
    }
}
