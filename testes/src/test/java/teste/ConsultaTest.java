package teste;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaTest {

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
                LocalDate.of(2025, 4, 8),
                "Clínica Central", new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Roger", "(81) 9999-99999", "Dra Katia"));

        Consulta consulta2 = new Consulta(
                LocalDateTime.of(2025, 4, 11, 0, 0),
                "Consulta B",
                new Material(),
                true,
                LocalDate.of(2025, 4, 10),
                "Hospital Geral",
                new Paciente(new PacienteId(1), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Marcos", "(81) 9999-99999", "Dra Roberta"));

        Consulta consulta3 = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta C",
                new Material(),
                false,
                LocalDate.of(2025, 4, 7),
                "Clínica Municipal",
                new Paciente(new PacienteId(2), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "João", "(81) 9999-99999", "Dra Matilda"));

        agenda.put("1", consultaBase);
        agenda.put("2", consulta2);
        agenda.put("3", consulta3);
    }


    // História N°5
    @Test
    void deveGerarNotificacoesDePagamentoAtrasado() {
        LocalDate hoje = LocalDate.of(2025, 4, 12);
        Map<String, String> notificacoes = Consulta.gerarNotificacoesPendencias(agenda, hoje);
        assertEquals(2, notificacoes.size());
        assertTrue(notificacoes.get("1").contains("atrasado há 4 dias"));
        assertTrue(notificacoes.get("3").contains("atrasado há 5 dias"));
    }

    // História N°6
    @Test
    void deveGerarLembrete1DiaAntecedencia() {
        LocalDate hoje = LocalDate.of(2025, 4, 9);
        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 4, 10, 14, 30),
                "Consulta Urgente",
                new Material(),
                false,
                LocalDate.of(2025, 4, 8),
                "Clínica Central",
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Martha", "(81) 9999-99999", "Dra Roberta"));
        agenda.put("4", consulta);

        Map<String, String> lembretes = Consulta.gerarLembretesConsultas(agenda, hoje);
        assertTrue(lembretes.get("4").contains("10/04/2025 às 14:30"));
        assertTrue(lembretes.get("4").contains("Clínica Central"));
    }

    @Test
    void deveGerarLembrete1SemanaAntecedencia() {
        Map<String, Consulta> agendaLocal = new HashMap<>();

        LocalDate hoje = LocalDate.of(2025, 4, 3);
        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 4, 10, 9, 0),
                "Consulta de Rotina",
                new Material(),
                true,
                LocalDate.of(2025, 4, 8),
                "Hospital Geral",
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Jorge", "(81) 9999-99999", "Dra Roberta"));
        agendaLocal.put("5", consulta);

        Map<String, String> lembretes = Consulta.gerarLembretesConsultas(agendaLocal, hoje);
        assertEquals(1, lembretes.size());
    }

    @Test
    void naoDeveGerarLembreteParaDatasForaDoIntervalo() {
        LocalDate hoje = LocalDate.of(2025, 4, 1);
        Consulta consulta = new Consulta(
                LocalDateTime.of(2025, 4, 5, 16, 0),
                "Consulta sem Lembrete",
                new Material(),
                true,
                LocalDate.of(2025, 4, 3),
                "Domicilio",
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Evaldo", "(81) 9999-99999", "Dra Roberta"));
        agenda.put("6", consulta);

        Map<String, String> lembretes = Consulta.gerarLembretesConsultas(agenda, hoje);
        assertFalse(lembretes.containsKey("6"));
    }
}
