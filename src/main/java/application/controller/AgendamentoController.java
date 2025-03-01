package application.controller;

import application.dao.AgendamentoDAO;
import application.model.Agendamento;
import application.util.SessaoUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.io.IOException;

public class AgendamentoController {

    @FXML
    private ComboBox<String> comboServicos;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> comboHorario;

    @FXML
    private Button btnAgendar;

    @FXML
    private Button btnVoltar;

    @FXML
    public void initialize() {
        carregarServicos();
        carregarHorarios();
    }

    private void carregarServicos() {
        ObservableList<String> servicos = FXCollections.observableArrayList(
                "Corte de Cabelo", "Massagem", "Manicure", "Pedicure", "Limpeza de Pele"
        );
        comboServicos.setItems(servicos);
    }

    private void carregarHorarios() {
        ObservableList<String> horarios = FXCollections.observableArrayList(
                "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00"
        );
        comboHorario.setItems(horarios);
    }

    @FXML
    private void agendarServico() {
        String servico = comboServicos.getValue();
        String data = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";
        String horario = comboHorario.getValue();

        if (servico == null || data.isEmpty() || horario == null) {
            mostrarAlerta("Erro", "Preencha todos os campos para agendar.");
            return;
        }

        // ✅ Obtém o email do usuário logado
        String clienteEmail = SessaoUsuario.getUsuarioLogado().getEmail();

        // ✅ Criando um agendamento com o construtor correto
        Agendamento agendamento = new Agendamento(clienteEmail, servico, data, horario);

        // ✅ Salvar o agendamento no banco de dados
        boolean sucesso = AgendamentoDAO.salvarAgendamento(agendamento);

        if (sucesso) {
            mostrarAlerta("Sucesso", "Serviço agendado com sucesso!\n\n"
                    + "Serviço: " + servico + "\n"
                    + "Data: " + data + "\n"
                    + "Horário: " + horario);
        } else {
            mostrarAlerta("Erro", "Erro ao agendar serviço. Tente novamente.");
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
