package notificacao;

import paciente.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ServicoPacientes {
    private static ServicoPacientes instace;
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<String> notificacoes = new ArrayList<>();

    private ServicoPacientes() {}

    public static synchronized ServicoPacientes getInstance() {
        if (instace == null) {
            instace = new ServicoPacientes();
        }
        return instace;
    }

    public void cadastrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        String notificacao = "Novo paciente cadastrado: " + paciente.getNome() + "por " + paciente.getMedicoResponsavel()+
                ". Contato: " + paciente.getContato();
        notificacoes.add(notificacao);
    }

    public List<String> getNotificacoes() {
        return notificacoes;
    }

    public void limparNotificacoes() {
        notificacoes.clear();
    }
}
