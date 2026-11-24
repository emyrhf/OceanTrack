package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.OcorrenciaPoluicao;

public class OcorrenciaPoluicaoDAOImpl implements OcorrenciaPoluicaoDAO {

    @Override
    public OcorrenciaPoluicao salvar(OcorrenciaPoluicao ocorrencia) {
        String sql = "INSERT INTO ocorrencias_poluicao "
                + "(tipo, local_ocorrencia, data_ocorrencia, gravidade, volume_estimado, descricao, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ocorrencia.getTipo());
            stmt.setString(2, ocorrencia.getLocal());
            stmt.setDate(3, Date.valueOf(ocorrencia.getData()));
            stmt.setString(4, ocorrencia.getGravidade());
            stmt.setBigDecimal(5, ocorrencia.getVolumeEstimado());
            stmt.setString(6, ocorrencia.getDescricao());
            stmt.setString(7, ocorrencia.getStatus());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    ocorrencia.setId(keys.getLong(1));
                }
            }
            return ocorrencia;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar ocorrência de poluição: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<OcorrenciaPoluicao> pesquisarTodos() {
        List<OcorrenciaPoluicao> lista = new ArrayList<>();
        String sql = "SELECT id, tipo, local_ocorrencia, data_ocorrencia, gravidade, volume_estimado, descricao, status "
                + "FROM ocorrencias_poluicao ORDER BY id ASC";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OcorrenciaPoluicao ocorrencia = new OcorrenciaPoluicao();
                ocorrencia.setId(rs.getLong("id"));
                ocorrencia.setTipo(rs.getString("tipo"));
                ocorrencia.setLocal(rs.getString("local_ocorrencia"));

                Date dataSql = rs.getDate("data_ocorrencia");
                ocorrencia.setData(dataSql != null ? dataSql.toLocalDate() : null);

                ocorrencia.setGravidade(rs.getString("gravidade"));
                ocorrencia.setVolumeEstimado(rs.getBigDecimal("volume_estimado"));
                ocorrencia.setDescricao(rs.getString("descricao"));
                ocorrencia.setStatus(rs.getString("status"));
                lista.add(ocorrencia);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar ocorrências de poluição: " + ex.getMessage(), ex);
        }

        return lista;
    }

    @Override
    public void atualizar(long id, OcorrenciaPoluicao ocorrencia) {
        String sql = "UPDATE ocorrencias_poluicao SET tipo = ?, local_ocorrencia = ?, data_ocorrencia = ?, "
                + "gravidade = ?, volume_estimado = ?, descricao = ?, status = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ocorrencia.getTipo());
            stmt.setString(2, ocorrencia.getLocal());
            stmt.setDate(3, Date.valueOf(ocorrencia.getData()));
            stmt.setString(4, ocorrencia.getGravidade());
            stmt.setBigDecimal(5, ocorrencia.getVolumeEstimado());
            stmt.setString(6, ocorrencia.getDescricao());
            stmt.setString(7, ocorrencia.getStatus());
            stmt.setLong(8, id);

            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Ocorrência de poluição com ID " + id + " não encontrada.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar ocorrência de poluição: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void remover(OcorrenciaPoluicao ocorrencia) {
        if (ocorrencia == null) {
            throw new IllegalArgumentException("Ocorrência de poluição não pode ser nula para remoção.");
        }

        String sql = "DELETE FROM ocorrencias_poluicao WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ocorrencia.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover ocorrência de poluição: " + ex.getMessage(), ex);
        }
    }
}
