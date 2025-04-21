package com.implantodontia.dominio.consulta.services;

import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.paciente.Paciente;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NotificacaoPacienteServico {

    public String verificarConsultasProximasDia(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome())) {
                LocalDate dataConsulta = entry.getValue().getDataHora().toLocalDate();
                if (dataConsulta.isBefore(datafiltro.plusDays(1)) && dataConsulta.isAfter(datafiltro.minusDays(2))){
                    return ("Consulta próxima para amanhã: " + entry.getValue().getPaciente().getNome() + " - Dia " + entry.getValue().getDataHora().toLocalDate());
                }
            }
        }

        return "Não existem consultas próximas para o paciente";
    }

    public String verificarConsultasProximasSemana(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome())) {
                LocalDate dataConsulta = entry.getValue().getDataHora().toLocalDate();
                if (dataConsulta.isBefore(datafiltro.plusDays(7)) && dataConsulta.isAfter(datafiltro.minusDays(2))){
                    return ("Consulta próxima para a Próxima semana: " + entry.getValue().getPaciente().getNome() + " - Dia " + entry.getValue().getDataHora().toLocalDate());
                }
            }
        }

        return "Não existem consultas próximas para o paciente";
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
}
