package application.controller;

import application.model.Usuario;
import application.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardClienteController implements Initializable {

    @FXML
    private Button btnPerfil;

    @FXML
    private void abrirTelaPerfil() {
        try {
            System.out.println("Abrindo tela de Perfil do Cliente..."); // Depuração

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PerfilCliente.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnPerfil.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            System.out.println("Tela de Perfil carregada!"); // Depuração
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir a tela de Perfil: " + e.getMessage());
        }
    }

    @FXML
    private Label labelNomeUsuario;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnAgendar;

    @FXML
    private Button btnHistorico;

    @FXML
    private void verServicos() {
        carregarTela("/views/Servicos.fxml");
    }

    @FXML
    private void meusAgendamentos() {
        carregarTela("/views/HistoricoAgendamentos.fxml");
    }

    @FXML
    private void editarPerfil() {
        carregarTela("/views/PerfilCliente.fxml");
    }

    @FXML
    private void sair() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação de Saída");
        alerta.setHeaderText("Tem certeza que deseja sair?");
        alerta.setContentText("Sua sessão será encerrada.");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            limparDadosUsuario();
            voltarParaLogin();
        }
    }

    private void limparDadosUsuario() {
        SessaoUsuario.limparSessao();
        System.out.println("Usuário deslogado.");
    }

    private void voltarParaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para o login: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        if (usuario != null) {
            labelNomeUsuario.setText("Bem-vindo, " + usuario.getNome() + "!");
        }
    }

    @FXML
    private void abrirTelaAgendamento() {
        carregarTela("/views/Agendamento.fxml");
    }

    @FXML
    private void abrirTelaHistorico() {
        try {
            System.out.println("Abrindo tela de Histórico de Agendamentos..."); // Depuração

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HistoricoAgendamentos.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) btnHistorico.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            System.out.println("Tela de Histórico carregada!"); // Depuração
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir a tela de Histórico: " + e.getMessage());
        }
    }

    private void carregarTela(String caminho) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
