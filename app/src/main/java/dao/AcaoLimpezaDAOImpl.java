package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.AcaoLimpeza;

public class AcaoLimpezaDAOImpl implements AcaoLimpezaDAO {

    @Override
    public AcaoLimpeza salvar(AcaoLimpeza acao) {
        String sql = "INSERT INTO acoes_limpeza "
                + "(nome, data_acao, local_acao, numero_participantes, quantidade_lixo_kg, organizador, tipo_residuo_predominante) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, acao.getNome());
            stmt.setDate(2, Date.valueOf(acao.getData()));
            stmt.setString(3, acao.getLocal());
            stmt.setInt(4, acao.getNumeroParticipantes());
            stmt.setBigDecimal(5, acao.getQuantidadeLixoKg());
            stmt.setString(6, acao.getOrganizador());
            stmt.setString(7, acao.getTipoResiduoPredominante());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    acao.setId(keys.getLong(1));
                }
            }
            return acao;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar ação de limpeza: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<AcaoLimpeza> pesquisarTodos() {
        List<AcaoLimpeza> lista = new ArrayList<>();
        String sql = "SELECT id, nome, data_acao, local_acao, numero_participantes, quantidade_lixo_kg, "
                + "organizador, tipo_residuo_predominante FROM acoes_limpeza ORDER BY id ASC";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AcaoLimpeza acao = new AcaoLimpeza();
                acao.setId(rs.getLong("id"));
                acao.setNome(rs.getString("nome"));

                Date dataSql = rs.getDate("data_acao");
                acao.setData(dataSql != null ? dataSql.toLocalDate() : null);

                acao.setLocal(rs.getString("local_acao"));
                acao.setNumeroParticipantes(rs.getInt("numero_participantes"));
                acao.setQuantidadeLixoKg(rs.getBigDecimal("quantidade_lixo_kg"));
                acao.setOrganizador(rs.getString("organizador"));
                acao.setTipoResiduoPredominante(rs.getString("tipo_residuo_predominante"));
                lista.add(acao);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar ações de limpeza: " + ex.getMessage(), ex);
        }

        return lista;
    }

    @Override
    public void atualizar(long id, AcaoLimpeza acao) {
        String sql = "UPDATE acoes_limpeza SET nome = ?, data_acao = ?, local_acao = ?, numero_participantes = ?, "
                + "quantidade_lixo_kg = ?, organizador = ?, tipo_residuo_predominante = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, acao.getNome());
            stmt.setDate(2, Date.valueOf(acao.getData()));
            stmt.setString(3, acao.getLocal());
            stmt.setInt(4, acao.getNumeroParticipantes());
            stmt.setBigDecimal(5, acao.getQuantidadeLixoKg());
            stmt.setString(6, acao.getOrganizador());
            stmt.setString(7, acao.getTipoResiduoPredominante());
            stmt.setLong(8, id);

            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Ação de limpeza com ID " + id + " não encontrada.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar ação de limpeza: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void remover(AcaoLimpeza acao) {
        if (acao == null) {
            throw new IllegalArgumentException("Ação de limpeza não pode ser nula para remoção.");
        }

        String sql = "DELETE FROM acoes_limpeza WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, acao.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover ação de limpeza: " + ex.getMessage(), ex);
        }
    }
}
