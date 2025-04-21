    package com.implantodontia.testes;

    import com.implantodontia.dominio.consulta.Consulta;
    import com.implantodontia.dominio.consulta.Material;
    import com.implantodontia.dominio.consulta.paciente.Paciente;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
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