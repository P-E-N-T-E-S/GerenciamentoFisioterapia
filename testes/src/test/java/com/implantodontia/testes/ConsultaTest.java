package com.implantodontia.testes;

import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
                LocalDate.of(2025, 4, 10),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 8));

        Consulta consulta2 = new Consulta(
                LocalDate.of(2025, 4, 11),
                "Consulta B",
                new Material(),
                true,
                LocalDate.of(2025, 4, 10));

        Consulta consulta3 = new Consulta(
                LocalDate.of(2025, 4, 10),
                "Consulta C",
                new Material(),
                false,
                LocalDate.of(2025, 4, 7));

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
}

