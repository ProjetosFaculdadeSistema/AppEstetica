package application.controller;

import application.dao.UsuarioDAO;
import application.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class CadastroController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private PasswordField confirmarSenhaField;

    public void handleCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            mostrarAlerta("Erro", "Todos os campos são obrigatórios!");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mostrarAlerta("Erro", "As senhas não coincidem!");
            return;
        }

        // Agora o usuário é salvo no banco de dados
        Usuario usuario = new Usuario(nome, email, senha);
        boolean sucesso = UsuarioDAO.cadastrarUsuario(usuario);

        if (sucesso) {
            mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!");
            limparCampos();
        } else {
            mostrarAlerta("Erro", "Erro ao cadastrar usuário!");
        }
    }

    public void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) nomeField.getScene().getWindow();
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

    private void limparCampos() {
        nomeField.clear();
        emailField.clear();
        senhaField.clear();
        confirmarSenhaField.clear();
    }
}
