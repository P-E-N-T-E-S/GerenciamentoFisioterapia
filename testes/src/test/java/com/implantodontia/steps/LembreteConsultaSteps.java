package com.implantodontia.steps;

import io.cucumber.java.pt.*;
import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.Material;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class LembreteConsultaSteps {

    private Map<String, Consulta> agenda;
    private Map<String, String> lembretes;

    // ==================================================================
    // Cenário: Receber lembrete com 1 dia de antecedência
    // ==================================================================
    @Dado("existe uma consulta agendada para {string} no local {string} com 1 dia de antecedência")
    public void criarConsulta1Dia(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta Urgente",
                new Material(),
                false,
                LocalDate.now().plusDays(10),
                local,
                new Paciente("Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("1", consulta);
    }

    @Quando("eu acesso o sistema no dia {string}")
    public void acessarSistema1Dia(String dataAcessoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAcesso = LocalDate.parse(dataAcessoStr, formatter);
        lembretes = Consulta.gerarLembretesConsultas(agenda, dataAcesso);
    }

    @Então("devo ver um lembrete com:")
    public void verificarLembrete1Dia(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta 'Consulta Urgente' agendada para %s às %s em %s",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertTrue(lembretes.containsValue(mensagemEsperada));
    }

    // ==================================================================
    // Cenário: Receber lembrete com 1 semana de antecedência
    // ==================================================================
    @Dado("existe uma consulta agendada para {string} no local {string} com 1 semana de antecedência")
    public void criarConsulta1Semana(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta de Rotina",
                new Material(),
                true,
                LocalDate.now().plusDays(10),
                local,
                new Paciente("Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("2", consulta);
    }

    @Então("devo receber um lembrete com:")
    public void verificarLembrete1Semana(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta 'Consulta de Rotina' agendada para %s às %s em %s",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertEquals(1, lembretes.size());
        assertTrue(lembretes.containsValue(mensagemEsperada));
    }

    // ==================================================================
    // Cenário: Não receber lembrete para datas fora do intervalo
    // ==================================================================
    @Dado("existe uma consulta agendada para {string} no local {string} sem lembrete agendado")
    public void criarConsultaSemLembrete(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta sem Lembrete",
                new Material(),
                true,
                LocalDate.now().plusDays(10),
                local,
                new Paciente("Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("3", consulta);
    }

    @Então("nenhum lembrete deve ser gerado")
    public void verificarAusenciaLembrete() {
        assertTrue(lembretes.isEmpty());
    }
}