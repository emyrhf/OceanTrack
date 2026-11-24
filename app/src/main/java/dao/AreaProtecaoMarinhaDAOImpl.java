package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.AreaProtecaoMarinha;

public class AreaProtecaoMarinhaDAOImpl implements AreaProtecaoMarinhaDAO {

    private static final String URL = "jdbc:mariadb://localhost:3306/oceantrack";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @Override
    public AreaProtecaoMarinha salvar(AreaProtecaoMarinha area) {
        String sql = "INSERT INTO area_protecao_marinha (nome, estado, area_km2, data_cadastro, orgao_responsavel, ativa) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, area.getNome());
            stmt.setString(2, area.getEstado());
            stmt.setInt(3, area.getArea());
            LocalDate dataCad = area.getDataCadastro() != null ? area.getDataCadastro() : LocalDate.now();
            stmt.setDate(4, Date.valueOf(dataCad));
            stmt.setString(5, area.getOrgaoResponavel());
            stmt.setBoolean(6, area.isAtiva());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        area.setId(keys.getLong(1));
                    }
                }
                area.setDataCadastro(dataCad);
                return area;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar área de proteção marinha no banco: " + ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<AreaProtecaoMarinha> pesquisarTodos() {
        List<AreaProtecaoMarinha> lista = new ArrayList<>();
        String sql = "SELECT id, nome, estado, area_km2, data_cadastro, orgao_responsavel, ativa "
                   + "FROM area_protecao_marinha ORDER BY id ASC";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AreaProtecaoMarinha area = new AreaProtecaoMarinha();
                area.setId(rs.getLong("id"));
                area.setNome(rs.getString("nome"));
                area.setEstado(rs.getString("estado"));
                area.setAreaKm2(rs.getInt("area_km2"));

                Date dataSql = rs.getDate("data_cadastro");
                area.setDataCadastro(dataSql != null ? dataSql.toLocalDate() : null);

                area.setOrgaoResponavel(rs.getString("orgao_responsavel"));
                area.setAtiva(rs.getBoolean("ativa"));

                lista.add(area);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar áreas de proteção marinha: " + ex.getMessage(), ex);
        }

        return lista;
    }

    @Override
    public void atualizar(long id, AreaProtecaoMarinha area) {
        String sql = "UPDATE area_protecao_marinha "
                   + "SET nome = ?, estado = ?, area_km2 = ?, data_cadastro = ?, orgao_responsavel = ?, ativa = ? "
                   + "WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, area.getNome());
            stmt.setString(2, area.getEstado());
            stmt.setInt(3, area.getArea());
            LocalDate dataCad = area.getDataCadastro() != null ? area.getDataCadastro() : LocalDate.now();
            stmt.setDate(4, Date.valueOf(dataCad));
            stmt.setString(5, area.getOrgaoResponavel());
            stmt.setBoolean(6, area.isAtiva());
            stmt.setLong(7, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar área de proteção marinha: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void remover(AreaProtecaoMarinha area) {
        if (area == null) {
            return;
        }

        String sql = "DELETE FROM area_protecao_marinha WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, area.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao remover área de proteção marinha: " + ex.getMessage(), ex);
        }
    }
}