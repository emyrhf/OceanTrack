package entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pesquisador {
private long id;
private String nome;
private String cpf;
private String telefone;
private String email;
private String instituicao;
private String tipo;
private LocalDate dataIngresso;

private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

public Pesquisador(){
    this(0, "", "", "", "", "", "PEQUISADOR" );
}

public Pesquisador(long id, String nome, String cpf, String telefone, String email, String instituicao, String tipo){
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
    this.email = email;
    this.instituicao = instituicao;
    this.tipo = tipo;
    this.dataIngresso = LocalDate.now();
}

public long getId(){
    return this.id;
}

public String getNome(){
    return this.nome;
}

public String getCpf(){
    return this.cpf;
}

public String getTelefone(){
    return this.telefone;
}

public String getEmail(){
    return this.email;
}

public String getInstituicao(){
    return this.instituicao;
}

public String getTipo(){
    return this.tipo;
}

public LocalDate getDataIngresso(){
    return this.dataIngresso;
}



//set



public void setId(long id){
    this.id = id;
}

public void setNome(String nome){
    this.nome = nome;
}

public void setCpf(String cpf){
    this.cpf = cpf;
}

public void setTelefone(String telefone){
    this.telefone = telefone;
}

public void setEmail(String email){
    this.email = email;
}

 public void setInstituicao(String instituicao){
    this.instituicao = instituicao;
 }

 public void setTipo(String tipo){
    this.tipo = tipo;
 }

 public void setDataIngresso(LocalDate dataIngresso){
    this.dataIngresso = dataIngresso;
 }

 @Override
public String toString() {
    String dataI = this.dataIngresso.format(formatador);
    return String.format("%d, %s, %s, %s, %s, %s, %s, %s", this.id, this.nome, this.cpf, this.telefone, this.email, this.instituicao, this.tipo, dataI);
}
}