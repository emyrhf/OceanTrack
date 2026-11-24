package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AreaProtecaoMarinha{
    private long id;
    private String nome;
    private String estado;
    private int areaKm2;
    private LocalDate dataCadastro;
    private String orgaoResponavel;
    private boolean ativa;

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AreaProtecaoMarinha(){
        this(0," ", " ", 0, " ");
    }

    public AreaProtecaoMarinha(long id, String nome, String estado, int areaKm2, String orgaoResponsavel){
        this.id = id;
        this.nome = nome;
        this.estado = estado;
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

    public int getArea(){
        return this.areaKm2;
    }

    public LocalDate getDataCadastro(){
        return this.dataCadastro;
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


    public void setAreaKm2(int areaKm2){
        this.areaKm2 = areaKm2;
    }

    public void setOrgaoResponavel(String orgaoResponavel){
        this.orgaoResponavel = orgaoResponavel;
    }  

    public void setAtiva(boolean ativa){
        this.ativa = ativa;
    }

    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    @Override public String toString(){
        String dataC = this.dataCadastro.format(formatador);
        return String.format("%d, %s, %s, %s, %s, %s, %s", this.id, this.nome, this.estado, this.areaKm2, dataC, this.orgaoResponavel, this.ativa);
    }
}