package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Pesquisador;

public class PesquisadorDAOImpl implements PesquisadorDAO {


    @Override
    public Pesquisador salvar(Pesquisador p) {
        String sql = "INSERT INTO pesquisadores (nome, cpf, email, telefone, instituicao, tipo, data_ingresso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getCpf());
        stmt.setString(3, p.getEmail());
        stmt.setString(4, p.getTelefone());
        stmt.setString(5, p.getInstituicao());
        stmt.setString(6, p.getTipo());
        stmt.setDate(7, Date.valueOf(p.getDataIngresso()));
        stmt.executeUpdate();
        
        try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
        p.setId(rs.getLong(1));
            }
        }
        return p;
    } catch (SQLException ex) {
    throw new RuntimeException("Erro ao salvar pesquisador: " + ex.getMessage(), ex);
    }
    }


    @Override 
    public void atualizar(long id, Pesquisador p){
        String sql = "UPDATE pesquisadores SET nome = ?, cpf = ?, email = ?, telefone = ?, instituicao = ?, tipo = ?";
        try (Connection con = Conexao.getConnection(); 
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, p.getNome());
                stmt.setString(2, p.getCpf());
                stmt.setString(3, p.getEmail());
                stmt.setString(4, p.getTelefone());
                stmt.setString(5, p.getInstituicao());
                stmt.setString(6, p.getTipo());
                stmt.setLong(7, id);
                stmt.executeUpdate();
            } catch (SQLException ex){
                throw new RuntimeException("Erro ao atualizar Pesquisador com o id " + id + ": " + ex.getMessage(), ex);
            }
    }

    @Override 
    public void remover(Pesquisador p){
        if( p == null){
            throw new IllegalArgumentException("não há como remover algo, pois o Pesquisador não existe.");
        }
        String sql = "DELETE FROM pesquisadores WHERE id = ?";
        try (Connection con = Conexao.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, p.getId());
            stmt.executeUpdate();
        } catch(SQLException ex){
             throw new RuntimeException("Erro ao remover o pesquisador com o ID: " + p.getId() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public List <Pesquisador> pesquisarTodos(){
         List<Pesquisador> lista = new ArrayList<>();
        String sql = "SELECT * FROM pesquisadores";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pesquisador p = new Pesquisador();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                p.setInstituicao(rs.getString("instituicao"));
                p.setTipo(rs.getString("tipo"));

                Date dIng = rs.getDate("data_ingresso");
                if (dIng != null) {
                    p.setDataIngresso(dIng.toLocalDate());
                }
                lista.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar pesquisadores: " + ex.getMessage(), ex);
            }
        return lista;
    }
    
}