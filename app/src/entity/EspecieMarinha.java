package entity;

import java.time.LocalDate;

public class EspecieMarinha{
    private long id;
    private String nomeComum;
    private String nomeCientifico;
    private String habitat;
    private double pesoMedioKg;
    private double tamanhoMedioCm;
    private String statusConservacao;
    private LocalDate dataCadastro;
    private LocalDate dataUltimaAtualizacao;

    public EspecieMarinha(){
        this(0, " ", " ", " ", 0.0, 0.0, " ");
    }

    public EspecieMarinha(long id, String nomeComum, String nomeCientifico, String habitat, double pesoMedioKg, double tamanhoMedioCm, String statusConservacao)
    {
        this.id = id;
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.habitat = habitat;
        this.pesoMedioKg = pesoMedioKg;
        this.tamanhoMedioCm = tamanhoMedioCm;
        this.statusConservacao = statusConservacao;
        this.dataCadastro = LocalDate.now();
        this.dataUltimaAtualizacao = LocalDate.now();
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

    public double getPesoMedioKg(){
        return this.pesoMedioKg;
    }

    public double getTamanhoMedioCm(){
        return this.tamanhoMedioCm;
    }

    public String getStatusConservacao(){
        return this.statusConservacao;
    }

    public LocalDate getDataCadastro(){
        return this.dataCadastro;
    }

    public LocalDate getDataUltimaAtualizacao(){
        return this.dataUltimaAtualizacao;
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

    public void setPesoMedioKg(double pesoMedioKg){
        this.pesoMedioKg = pesoMedioKg;
    }

    public void setTamanhoMedioCm(double tamanhoMedioCm){
        this.tamanhoMedioCm = tamanhoMedioCm;
    }

    public void setStatusConservacao(String statusConservacao){
        this.statusConservacao = statusConservacao;
    }

    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao){
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    // @Override public String toString(){
    //     return ;
    // }
}