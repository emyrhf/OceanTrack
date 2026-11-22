package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mariadb://localhost:3306/oceantrack";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // public static void main(String[] args) {
    //     try (Connection conn = getConnection()) {
    //         if (conn != null) {
    //             System.out.println("Conectado com sucesso!");
    //         }
    //     } catch (SQLException e) {
    //         System.out.println("Erro ao conectar: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
}
