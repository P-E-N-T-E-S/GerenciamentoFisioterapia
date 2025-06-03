package com.implantodontia.dominio.support.notificacoes;

import java.time.LocalDate;
import java.util.Map;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoPagamentoPacienteServico {

    public String verificarPagamentoPendenteDia(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome()) && !entry.getValue().getClientePagou()) {
                LocalDate dataVencimento = entry.getValue().getDataVencimento();
                if (dataVencimento.isEqual(datafiltro.plusDays(1)) && dataVencimento.isAfter(datafiltro.minusDays(2))){
                    return ("Pagamento pendente do paciente: " + entry.getValue().getPaciente().getNome() + " - para o dia " + entry.getValue().getDataVencimento());
                }
            }
        }

        return "Não existem pagamentos pendentes para o paciente";
    }

    public String verificarPagamentoPendenteSemana(Paciente paciente, Map<String, Consulta> agenda, LocalDate datafiltro){
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            Consulta consulta = entry.getValue();
            String nome = entry.getValue().getPaciente().getNome();
            if (nome.equals(paciente.getNome()) && !consulta.getClientePagou()) {
                LocalDate dataVencimento = entry.getValue().getDataVencimento();
                if (dataVencimento.isEqual(datafiltro.plusDays(7)) && dataVencimento.isAfter(datafiltro.minusDays(2))){
                    return ("Pagamento pendente do paciente: " + entry.getValue().getPaciente().getNome() + " - para o dia " + entry.getValue().getDataVencimento());
                }
            }
        }

        return "Não existem pagamentos pendentes para o paciente";
    }
}
