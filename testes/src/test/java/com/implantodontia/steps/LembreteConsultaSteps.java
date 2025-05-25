package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import io.cucumber.java.en.*;
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
    @Given("existe uma consulta agendada para {string} no local {string} com 1 dia de antecedência")
    public void criarConsulta1Dia(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta Urgente",
                new Material(1, "Gaze"),
                false,
                LocalDate.now().plusDays(10),
                local,
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("1", consulta);
    }

    @When("eu acesso o sistema no dia {string}")
    public void acessarSistema1Dia(String dataAcessoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAcesso = LocalDate.parse(dataAcessoStr, formatter);
        lembretes = Consulta.gerarLembretesConsultas(agenda, dataAcesso);
    }

    @Then("devo ver um lembrete com:")
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
    @Given("existe uma consulta agendada para {string} no local {string} com 1 semana de antecedência")
    public void criarConsulta1Semana(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta de Rotina",
                new Material(1, "Gaze"),
                true,
                LocalDate.now().plusDays(10),
                local,
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("2", consulta);
    }

    @Then("devo receber um lembrete com:")
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
    @Given("existe uma consulta agendada para {string} no local {string} sem lembrete agendado")
    public void criarConsultaSemLembrete(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                dataHora,
                "Consulta sem Lembrete",
                new Material(1, "Gaze"),
                true,
                LocalDate.now().plusDays(10),
                local,
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Paciente Teste", "0000-0000", "Dr. Teste")
        );
        agenda.put("3", consulta);
    }

    @Then("nenhum lembrete deve ser gerado")
    public void verificarAusenciaLembrete() {
        assertTrue(lembretes.isEmpty());
    }
}