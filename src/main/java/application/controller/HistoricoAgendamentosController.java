package application.controller;

import application.dao.AgendamentoDAO;
import application.model.Agendamento;
import application.util.SessaoUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HistoricoAgendamentosController {

    @FXML
    private TableView<Agendamento> tabelaAgendamentos;

    @FXML
    private TableColumn<Agendamento, String> colServico;

    @FXML
    private TableColumn<Agendamento, String> colData;

    @FXML
    private TableColumn<Agendamento, String> colHorario;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnRemover;

    @FXML
    public void initialize() {
        colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        carregarAgendamentos();
    }

    private void carregarAgendamentos() {
        String clienteEmail = SessaoUsuario.getUsuarioLogado().getEmail();
        List<Agendamento> agendamentos = AgendamentoDAO.listarAgendamentosPorCliente(clienteEmail);

        ObservableList<Agendamento> lista = FXCollections.observableArrayList(agendamentos);
        tabelaAgendamentos.setItems(lista);
    }

    @FXML
    private void removerAgendamento() {
        Agendamento agendamentoSelecionado = tabelaAgendamentos.getSelectionModel().getSelectedItem();

        if (agendamentoSelecionado == null) {
            mostrarAlerta("Erro", "Selecione um agendamento para remover.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Remover Agendamento");
        alerta.setHeaderText("Tem certeza que deseja remover este agendamento?");
        alerta.setContentText("Essa ação não pode ser desfeita.");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            boolean sucesso = AgendamentoDAO.removerAgendamento(agendamentoSelecionado.getId());
            if (sucesso) {
                mostrarAlerta("Sucesso", "Agendamento removido com sucesso.");
                carregarAgendamentos();
            } else {
                mostrarAlerta("Erro", "Erro ao remover agendamento.");
            }
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
