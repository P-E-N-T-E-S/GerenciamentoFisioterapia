package teste;

import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.Material;
import com.implantodontia.dominio.consulta.Procedimento;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import com.implantodontia.dominio.consulta.services.HistoricoProcedimentosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class HistoricoProcedimentosTest {

    private HistoricoProcedimentosService service;

    @BeforeEach
    void setUp() {
        service = HistoricoProcedimentosService.getInstance();
        service.limparProcedimentos();
    }

    @Test
    void deveRetornarNenhumProcedimentoParaDataEspecifica() {
        LocalDate dataConsulta = LocalDate.of(2020, 8, 29);
        List<Procedimento> resultado = service.filtrarPorDataExata(dataConsulta);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveFiltrarProcedimentosPorTipo() {
        service.adicionarProcedimento(new Procedimento(
                LocalDate.now(),
                Procedimento.TipoProcedimento.INTRA_OPERATORIO,
                "Procedimento intraoperatório de reabilitação",
                "Hospital Geral"
        ));

        service.adicionarProcedimento(new Procedimento(
                LocalDate.now(),
                Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                "Consulta Domiciliar",
                "Rua dos Principes"
        ));

        List<Procedimento> resultado = service.filtrarPorTipo(
                Procedimento.TipoProcedimento.INTRA_OPERATORIO
        );

        assertEquals(1, resultado.size());
        assertEquals("Hospital Geral", resultado.get(0).getLocal());
    }

    @Test
    void deveFiltrarPorMesEAno() {
        service.adicionarProcedimento(new Procedimento(
                LocalDate.of(2024, 3, 15),
                Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                "Recuperação pós-artroscopia no consultório",
                "Consultório"
        ));

        List<Procedimento> resultado = service.filtrarPorMesAno(3, 2024);
        assertEquals(1, resultado.size());
        assertEquals(Procedimento.TipoProcedimento.CONSULTA_PADRAO, resultado.get(0).getTipo());
    }

    static class ConsultaTest {

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
                    "Clínica Central", new Paciente("Roger", "(81) 9999-99999", "Dra Katia"));

            Consulta consulta2 = new Consulta(
                    LocalDateTime.of(2025, 4, 11, 0, 0),
                    "Consulta B",
                    new Material(),
                    true,
                    LocalDate.of(2025, 4, 10),
                    "Hospital Geral",
            new Paciente("Marcos", "(81) 9999-99999", "Dra Roberta"));

            Consulta consulta3 = new Consulta(
                    LocalDateTime.of(2025, 4, 10, 0, 0),
                    "Consulta C",
                    new Material(),
                    false,
                    LocalDate.of(2025, 4, 7),
                    "Clínica Municipal",
                    new Paciente("João", "(81) 9999-99999", "Dra Matilda"));

            agenda.put("1", consultaBase);
            agenda.put("2", consulta2);
            agenda.put("3", consulta3);
        }

        @Test
        void testFiltrarPorData_encontraConsultasComDataEspecifica() {
            Map<String, Consulta> resultado = consultaBase.filtrarPorData(agenda, LocalDate.of(2025, 4, 10));
            assertEquals(2, resultado.size());
            assertTrue(resultado.containsKey("1"));
            assertTrue(resultado.containsKey("3"));
        }

        @Test
        void testFiltrarPorData_nenhumaConsultaComData() {
            Map<String, Consulta> resultado = consultaBase.filtrarPorData(agenda, LocalDate.of(2025, 5, 1));
            assertTrue(resultado.isEmpty());
        }

        @Test
        void deveGerarNotificacoesDePagamentoAtrasado() {
            LocalDate hoje = LocalDate.of(2025, 4, 12);
            Map<String, String> notificacoes = Consulta.gerarNotificacoesPendencias(agenda, hoje);
            assertEquals(2, notificacoes.size());
            assertTrue(notificacoes.get("1").contains("atrasado há 4 dias"));
            assertTrue(notificacoes.get("3").contains("atrasado há 5 dias"));
        }

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
                    new Paciente("Martha", "(81) 9999-99999", "Dra Roberta"));
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
            new Paciente("Jorge", "(81) 9999-99999", "Dra Roberta"));
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
                    new Paciente("Evaldo", "(81) 9999-99999", "Dra Roberta"));
            agenda.put("6", consulta);

            Map<String, String> lembretes = Consulta.gerarLembretesConsultas(agenda, hoje);
            assertFalse(lembretes.containsKey("6"));
        }
    }
}