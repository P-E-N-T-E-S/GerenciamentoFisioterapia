package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;

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
        if (paciente.getContato() == null || paciente.getContato().isBlank()) {
            throw new IllegalArgumentException("Contato obrigatório");
        }
        pacientes.add(paciente);
        String notificacao = String.format(
                "Alerta: Novo paciente cadastrado por %s. Nome: %s, Contato: %s",
                paciente.getMedicoResponsavel(),
                paciente.getNome(),
                paciente.getContato()
        );
        notificacoes.add(notificacao);
    }

    public String verificarPagamentoPendenteDia(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome()) && !entry.getValue().getClientePagou()) {
                LocalDate dataVencimento = entry.getValue().getDataVencimento();
                if (dataVencimento.isBefore(datafiltro.plusDays(1)) && dataVencimento.isAfter(datafiltro.minusDays(2))){
                    return ("Pagamento pendente do paciente: " + entry.getValue().getPaciente().getNome() + " - para o dia " + entry.getValue().getDataHora().toLocalDate());
                }
            }
        }

        return "Não existem pagamentos pendentes para o paciente";
    }

    public String verificarPagamentoPendenteSemana(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome())) {
                LocalDate dataVencimento = entry.getValue().getDataVencimento();
                if (dataVencimento.isBefore(datafiltro.plusDays(7)) && dataVencimento.isAfter(datafiltro.minusDays(2))){
                    return ("Pagamento pendente do paciente: " + entry.getValue().getPaciente().getNome() + " - para o dia " + entry.getValue().getDataHora().toLocalDate());
                }
            }
        }

        return "Não existem pagamentos pendentes para o paciente";
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<String> getNotificacoes() {
        return notificacoes;
    }

    public void limparNotificacoes() {
        notificacoes.clear();
    }
}
