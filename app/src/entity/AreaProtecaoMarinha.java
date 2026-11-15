package entity;

import java.time.LocalDate;

public class AreaProtecaoMarinha{
    private long id;
    private String nome;
    private String estado;
    private String municipio;
    private String tipo; 
    private int areaKm2;
    private LocalDate dataCadastro;
    private LocalDate dataInativo;
    private String orgaoResponavel;
    private boolean ativa;

    public AreaProtecaoMarinha(){
        this(0," ", " ", " ", " ", 0, " ");
    }

    public AreaProtecaoMarinha(long id, String nome, String estado, String municipio, String tipo, int areaKm2, String orgaoResponsavel){
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.municipio = municipio;
        this.tipo = tipo;
        this.areaKm2 = areaKm2;
        this.dataCadastro = LocalDate.now();
        this.orgaoResponavel = orgaoResponsavel;
        this.ativa = true;
    }

    // getters

    public long getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEstado(){
        return this.estado;
    }

    public String getMunicipio(){
        return this.municipio;
    }

    public String getTipo(){
        return this.tipo;
    }

    public int getAreaKm2(){
        return this.areaKm2;
    }

    public LocalDate getDataCadastro(){
        return this.dataCadastro;
    }

    public LocalDate getDataInativo(){
        return this.dataInativo;
    }

    public String getOrgaoResponavel(){
        return this.orgaoResponavel;
    }

    public boolean isAtiva(){
        return this.ativa;
    }

    // Setters: 
    public void setId(long id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setMunicipio(String municipio){
        this.municipio = municipio;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setAreaKm2(int areaKm2){
        this.areaKm2 = areaKm2;
    }

    public void setOrgaoResponavel(String orgaoResponavel){
        this.orgaoResponavel = orgaoResponavel;
    }  

    public void setAtiva(boolean ativa){
        this.ativa = ativa;
    }

    public void setDataInativo(LocalDate dataInativo){
        this.dataInativo = dataInativo;
    }

    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    public void setInativo(){
        this.ativa = false;
        this.dataInativo = LocalDate.now();
    }

    // @Override public String toString(){
    //     return 
    // }
}