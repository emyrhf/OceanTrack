package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EspecieMarinha{
    private long id;
    private String nomeComum;
    private String nomeCientifico;
    private String habitat;
    private String statusConservacao;
    private LocalDate dataCadastro;
    private LocalDate dataAtualizacao;

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EspecieMarinha(){
        this(0, " ", " ", " ", " ");
    }

    public EspecieMarinha(long id, String nomeComum, String nomeCientifico, String habitat, String statusConservacao)
    {
        this.id = id;
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.habitat = habitat;
        this.statusConservacao = statusConservacao;
        this.dataCadastro = LocalDate.now();
        this.dataAtualizacao = LocalDate.now();
    }
    
    public long getId(){
        return this.id;
    }

    public String getNomeComum(){
        return this.nomeComum;
    }

    public String getNomeCientifico(){
        return this.nomeCientifico;
    }

    public String getHabitat(){
        return this.habitat;
    }

    public String getStatusConservacao(){
        return this.statusConservacao;
    }

    public LocalDate getDataCadastro(){
        return this.dataCadastro;
    }

    public LocalDate getDataUltimaAtualizacao(){
        return this.dataAtualizacao;
    }

    // Setters: 

    public void setId(long id){
        this.id = id;
    }

    public void setNomeComum(String nomeComum){
        this.nomeComum = nomeComum;
    }

    public void setNomeCientifico(String nomeCientifico){
        this.nomeCientifico = nomeCientifico;
    }

    public void setHabitat(String habitat){
        this.habitat = habitat;
    }

    public void setStatusConservacao(String statusConservacao){
        this.statusConservacao = statusConservacao;
    }

    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao){
        this.dataAtualizacao = dataUltimaAtualizacao;
    }

    @Override public String toString(){
        String dataC = this.dataCadastro.format(formatador);
        String dataA = this.dataCadastro.format(formatador);
        return String.format("%d, %s, %s, %s, %s, %s, %s", this.id, this.nomeComum, this.nomeCientifico, this.habitat, this.statusConservacao, dataC, dataA);
    }
}