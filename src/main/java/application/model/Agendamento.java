package application.model;

public class Agendamento {
    private int id;
    private String clienteEmail;
    private String servico;
    private String data;
    private String horario;

    // Construtor completo (com ID)
    public Agendamento(int id, String clienteEmail, String servico, String data, String horario) {
        this.id = id;
        this.clienteEmail = clienteEmail;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
    }

    // Novo construtor sem ID (para criação de agendamentos)
    public Agendamento(String clienteEmail, String servico, String data, String horario) {
        this.clienteEmail = clienteEmail;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public String getServico() {
        return servico;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }
}
