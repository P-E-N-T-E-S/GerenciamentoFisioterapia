package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LembreteConsultaSteps {

    private Map<String, Consulta> agenda = new HashMap<>();
    private Map<String, String> lembretes = new HashMap<>();
    private LocalDate dataDeHoje;

    private void gerarLembretes() {
        lembretes.clear();
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            Consulta consulta = entry.getValue();
            long dias = java.time.temporal.ChronoUnit.DAYS.between(dataDeHoje, consulta.getDataHora().toLocalDate());
            if (dias == 1 || dias == 7) {
                String data = consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String hora = consulta.getDataHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                String local = consulta.getLocal().getNome();

                String mensagem = String.format("Lembrete: Consulta '%s' agendada para %s às %s em %s",
                        consulta.getDescricao(), data, hora, local);

                lembretes.put(entry.getKey(), mensagem);
            }
        }
    }

    @Given("existe uma consulta agendada para {string} no local {string} com 1 dia de antecedência")
    public void consultaCom1DiaDeAntecedencia(String dataHoraStr, String local) {
        agenda.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                criarPaciente(),
                LocalDate.now().plusDays(10),
                false,
                "Consulta Urgente",
                materiais,
                new Endereco(local, "Rua A", "123", "Sala 1", "Cidade", "00000-000")
        );
        agenda.put("1", consulta);
    }

    @Given("existe uma consulta agendada para {string} no local {string} com 1 semana de antecedência")
    public void consultaCom1SemanaDeAntecedencia(String dataHoraStr, String local) {
        agenda.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                criarPaciente(),
                LocalDate.now().plusDays(10),
                true,
                "Consulta de Rotina",
                materiais,
                new Endereco(local, "Rua B", "456", "Sala 2", "Cidade", "11111-111")
        );
        agenda.put("2", consulta);
    }

    @Given("existe uma consulta agendada para {string} no local {string} sem lembrete agendado")
    public void consultaSemLembrete(String dataHoraStr, String local) {
        agenda.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                criarPaciente(),
                LocalDate.now().plusDays(10),
                true,
                "Consulta sem Lembrete",
                materiais,
                new Endereco(local, "Rua C", "789", "Sala 3", "Cidade", "22222-222")
        );
        agenda.put("3", consulta);
    }

    @When("eu acesso o sistema no dia {string}")
    public void acessoOSistema(String dataHojeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataDeHoje = LocalDate.parse(dataHojeStr, formatter);
        gerarLembretes();
    }

    @Then("devo ver um lembrete com:")
    public void deveVerLembrete(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta '%s' agendada para %s às %s em %s",
                "Consulta Urgente",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertTrue(lembretes.containsValue(mensagemEsperada));
    }

    @Then("devo receber um lembrete com:")
    public void deveReceberLembrete(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta '%s' agendada para %s às %s em %s",
                "Consulta de Rotina",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertEquals(1, lembretes.size());
        assertTrue(lembretes.containsValue(mensagemEsperada));
    }

    @Then("nenhum lembrete deve ser gerado")
    public void nenhumLembreteGerado() {
        assertTrue(lembretes.isEmpty());
    }

    private Paciente criarPaciente() {
        return new Paciente(
                new PacienteId(0),
                new Cpf("684.976.720-89"),
                new Endereco("Endereço Padrão", "Rua Principal", "1", "Sala 10", "Recife", "52071-321"),
                "Paciente Teste",
                "0000-0000",
                "Dr. Teste"
        );
    }
}
