package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Expedicao;

public class ExpedicaoDAOImpl implements ExpedicaoDAO {

    @Override
    public Expedicao salvar(Expedicao e) {
        String sql = "INSERT INTO expedicoes (titulo, data_inicio, data_fim, localizacao, embarcacao, orcamento, objetivo, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(stmt, e);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
            return e;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar expedição: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(long id, Expedicao e) {
        String sql = "UPDATE expedicoes SET titulo = ?, data_inicio = ?, data_fim = ?, localizacao = ?, embarcacao = ?, orcamento = ?, objetivo = ?, status = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            preencher(stmt, e);
            stmt.setLong(9, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar expedição com ID " + id + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public void remover(Expedicao e) {
        if (e == null) {
            throw new IllegalArgumentException("Expedição não pode ser nula para remoção.");
        }
        String sql = "DELETE FROM expedicoes WHERE id = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover expedição com ID " + e.getId() + ": " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Expedicao> pesquisarTodos() {
        List<Expedicao> lista = new ArrayList<>();
        String sql = "SELECT * FROM expedicoes ORDER BY data_inicio DESC";
        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Expedicao e = new Expedicao();
                e.setId(rs.getLong("id"));
                e.setTitulo(rs.getString("titulo"));
                e.setDataInicio(rs.getDate("data_inicio").toLocalDate());

                Date dFim = rs.getDate("data_fim");
                e.setDataFim(dFim != null ? dFim.toLocalDate() : null);

                e.setLocalizacao(rs.getString("localizacao"));
                e.setEmbarcacao(rs.getString("embarcacao"));
                e.setOrcamento(rs.getBigDecimal("orcamento"));
                e.setObjetivo(rs.getString("objetivo"));
                e.setStatus(rs.getString("status"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar expedições: " + ex.getMessage(), ex);
        }
        return lista;
    }

    // Preenche os 8 primeiros "?" (iguais no INSERT e no UPDATE)
    private void preencher(PreparedStatement stmt, Expedicao e) throws SQLException {
        stmt.setString(1, e.getTitulo());
        stmt.setDate(2, Date.valueOf(e.getDataInicio()));
        stmt.setDate(3, e.getDataFim() != null ? Date.valueOf(e.getDataFim()) : null);
        stmt.setString(4, e.getLocalizacao());
        stmt.setString(5, e.getEmbarcacao());
        stmt.setBigDecimal(6, e.getOrcamento());
        stmt.setString(7, e.getObjetivo());
        stmt.setString(8, e.getStatus());
    }
}