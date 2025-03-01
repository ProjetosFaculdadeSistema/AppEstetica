package application.controller;

import application.dao.UsuarioDAO;
import application.model.Usuario;
import application.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class PerfilClienteController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        carregarDadosCliente();
    }

    private void carregarDadosCliente() {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        if (usuario != null) {
            nomeField.setText(usuario.getNome());
            emailField.setText(usuario.getEmail());
        }
    }

    @FXML
    private void salvarAlteracoes() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (nome.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Erro", "Nome e Email são obrigatórios.");
            return;
        }

        // Obtém o usuário atual
        Usuario usuarioAtual = SessaoUsuario.getUsuarioLogado();

        // Se o usuário alterar o email, impede a alteração (pois é a chave primária no banco)
        if (!usuarioAtual.getEmail().equals(email)) {
            mostrarAlerta("Erro", "Não é possível alterar o email.");
            emailField.setText(usuarioAtual.getEmail()); // Restaura o email antigo
            return;
        }

        // Atualiza os dados
        Usuario usuarioAtualizado = new Usuario(nome, email, senha);
        boolean sucesso = UsuarioDAO.atualizarPerfil(usuarioAtualizado);

        if (sucesso) {
            SessaoUsuario.setUsuarioLogado(usuarioAtualizado); // Atualiza os dados na sessão
            mostrarAlerta("Sucesso", "Perfil atualizado com sucesso!");
        } else {
            mostrarAlerta("Erro", "Erro ao atualizar perfil. Tente novamente.");
        }
    }

    @FXML
    private void voltarParaDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DashboardCliente.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
