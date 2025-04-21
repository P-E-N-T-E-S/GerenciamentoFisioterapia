package com.implantodontia.testes;

import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.Material;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import com.implantodontia.dominio.consulta.services.NotificacaoPacienteServico;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificacaoConsultaPacienteTest {

    private Map<String, Consulta> agenda;
    private Consulta consultaBase;

    @BeforeEach
    void setUp() {
        agenda = new HashMap<>();

        consultaBase = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 17),
                "Clínica Central", new Paciente("Roger", "(81) 9999-99999", "Dra Katia"));

        Consulta consulta2 = new Consulta(
                LocalDateTime.of(2025, 4, 11, 0, 0),
                "Consulta B",
                new Material(),
                true,
                LocalDate.of(2025, 4, 18),
                "Hospital Geral",
                new Paciente("Marcos", "(81) 9999-99999", "Dra Roberta"));

        Consulta consulta3 = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta C",
                new Material(),
                false,
                LocalDate.of(2025, 4, 17),
                "Clínica Municipal",
                new Paciente("João", "(81) 9999-99999", "Dra Matilda"));

        agenda.put("1", consultaBase);
        agenda.put("2", consulta2);
        agenda.put("3", consulta3);
    }

    @Test
    public void enviarNotificacaoConsultaProximaUmDia(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarConsultasProximasDia(new Paciente("Marcos", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 11));

        assertEquals("Consulta próxima para amanhã: Marcos - Dia 2025-04-11", validacao);
    }

    @Test
    public void enviarNotificacaoConsultaProximaUmaSemana(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarConsultasProximasSemana(new Paciente("João", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 10));

        assertEquals("Consulta próxima para a Próxima semana: João - Dia 2025-04-10", validacao);
    }

    @Test
    public void enviarNotificacaoNaoExistemConsultasProximas(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarConsultasProximasSemana(new Paciente("João", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 14));

        assertEquals("Não existem consultas próximas para o paciente", validacao);
    }


    @Test
    public void enviarNotificacaoPagamentoPendenteProximaUmDia(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarPagamentoPendenteDia(new Paciente("Roger", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 17));

        assertEquals("Pagamento pendente do paciente: Roger - para o dia 2025-04-10", validacao);
    }

    @Test
    public void pagamentoPendenteUmaSemana(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarPagamentoPendenteSemana(new Paciente("João", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 15));

        assertEquals("Pagamento pendente do paciente: João - para o dia 2025-04-10", validacao);
    }

    @Test
    public void enviarNotificacaoNaoExistePagamentoPendenteClientePagou(){
        NotificacaoPacienteServico s = new NotificacaoPacienteServico();
        String validacao = s.verificarPagamentoPendenteSemana(new Paciente("Betania", "(81) 9999-99999", "Dra Roberta"), agenda, LocalDate.of(2025, 4, 15));

        assertEquals("Não existem pagamentos pendentes para o paciente", validacao);
    }
}
