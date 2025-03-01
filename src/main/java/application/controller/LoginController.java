package application.controller;

import application.dao.UsuarioDAO;
import application.model.Usuario;
import application.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public void handleLogin() {
        String email = emailField.getText();
        String senha = passwordField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos!");
            return;
        }

        Usuario usuario = UsuarioDAO.autenticarUsuario(email, senha);

        if (usuario != null) {
            SessaoUsuario.setUsuarioLogado(usuario); // Salva os dados do usuário logado
            mostrarAlerta("Sucesso", "Bem-vindo, " + usuario.getNome() + "!");

            abrirDashboard(); //Chamando a função para abrir a Dashboard
        } else {
            mostrarAlerta("Erro", "Email ou senha incorretos.");
        }
    }

    private void abrirDashboard() {
        try {
            System.out.println("Tentando abrir a Dashboard..."); // 🔍 Depuração

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DashboardCliente.fxml"));
            Scene scene = new Scene(loader.load());

            System.out.println("Dashboard carregada!"); // 🔍 Depuração

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            System.out.println("Tela alterada para a Dashboard."); // 🔍 Depuração
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar Dashboard: " + e.getMessage()); // 🔍 Depuração
        }
    }

    public void handleCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Cadastro.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) emailField.getScene().getWindow();
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
