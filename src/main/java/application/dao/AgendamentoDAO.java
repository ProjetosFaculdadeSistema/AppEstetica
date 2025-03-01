package application.dao;

import application.model.Agendamento;
import application.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public static boolean salvarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (cliente_email, servico, data, horario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, agendamento.getClienteEmail());
            stmt.setString(2, agendamento.getServico());
            stmt.setString(3, agendamento.getData());
            stmt.setString(4, agendamento.getHorario());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar agendamento: " + e.getMessage());
            return false;
        }
    }

    public static List<Agendamento> listarAgendamentosPorCliente(String clienteEmail) {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT id, servico, data, horario FROM agendamentos WHERE cliente_email = ? ORDER BY data, horario";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clienteEmail);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                agendamentos.add(new Agendamento(
                        rs.getInt("id"),  // Agora passamos o ID corretamente
                        clienteEmail,
                        rs.getString("servico"),
                        rs.getString("data"),
                        rs.getString("horario")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }


    public static boolean removerAgendamento(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao remover agendamento: " + e.getMessage());
            return false;
        }
    }


}
