package application.util;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Erro ao conectar ao banco.");
        }
    }
}
