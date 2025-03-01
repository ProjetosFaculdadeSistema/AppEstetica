package application.dao;

import application.model.Usuario;
import application.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.util.Criptografia;
import application.util.SessaoUsuario;

public class UsuarioDAO {


    public static boolean cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Criptografa a senha antes de salvar no banco
            String senhaCriptografada = Criptografia.hashSenha(usuario.getSenha());

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, senhaCriptografada);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }


    public static Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaHash = rs.getString("senha");
                if (Criptografia.verificarSenha(senha, senhaHash)) {
                    Usuario usuario = new Usuario(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
                    SessaoUsuario.setUsuarioLogado(usuario); // 🔹 Armazena o usuário na sessão
                    return usuario;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
        }
        return null;
    }

    public static boolean atualizarPerfil(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, senha = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());

            // Se o usuário digitou uma nova senha, criptografamos antes de salvar
            String senhaCriptografada = usuario.getSenha().isEmpty() ? usuario.getSenha() : Criptografia.hashSenha(usuario.getSenha());
            stmt.setString(2, senhaCriptografada);

            stmt.setString(3, usuario.getEmail());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar perfil: " + e.getMessage());
            return false;
        }
    }


}
