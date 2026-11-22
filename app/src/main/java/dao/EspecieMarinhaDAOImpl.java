package dao;

import entity.EspecieMarinha;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecieMarinhaDAOImpl implements EspecieMarinhaDAO {

    @Override
    public EspecieMarinha salvar(EspecieMarinha e) {
        String sql = "INSERT INTO especies_marinhas (nome_comum, nome_cientifico, habitat, status_conservacao, data_cadastro, data_atualizacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, e.getNomeComum());
            stmt.setString(2, e.getNomeCientifico());
            stmt.setString(3, e.getHabitat());
            stmt.setString(4, e.getStatusConservacao());
            stmt.setDate(5, e.getDataCadastro() != null ? Date.valueOf(e.getDataCadastro()) : null);
            stmt.setDate(6, e.getDataUltimaAtualizacao() != null ? Date.valueOf(e.getDataUltimaAtualizacao()) : null);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
            return e;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(long id, EspecieMarinha e) {
        String sql = "UPDATE especies_marinhas SET nome_comum = ?, nome_cientifico = ?, habitat = ?, status_conservacao = ?, data_atualizacao = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, e.getNomeComum());
            stmt.setString(2, e.getNomeCientifico());
            stmt.setString(3, e.getHabitat());
            stmt.setString(4, e.getStatusConservacao());
            stmt.setDate(5, e.getDataUltimaAtualizacao() != null ? Date.valueOf(e.getDataUltimaAtualizacao()) : null);
            stmt.setLong(6, id);

            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remover(EspecieMarinha e) {
        String sql = "DELETE FROM especies_marinhas WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<EspecieMarinha> pesquisarTodos() {
        List<EspecieMarinha> lista = new ArrayList<>();
        String sql = "SELECT * FROM especies_marinhas";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EspecieMarinha e = new EspecieMarinha();
                e.setId(rs.getLong("id"));
                e.setNomeComum(rs.getString("nome_comum"));
                e.setNomeCientifico(rs.getString("nome_cientifico"));
                e.setHabitat(rs.getString("habitat"));
                e.setStatusConservacao(rs.getString("status_conservacao"));

                Date dCad = rs.getDate("data_cadastro");
                if (dCad != null) {
                    e.setDataCadastro(dCad.toLocalDate());
                }

                Date dAtu = rs.getDate("data_atualizacao");
                if (dAtu != null) {
                    e.setDataUltimaAtualizacao(dAtu.toLocalDate());
                }

                lista.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}