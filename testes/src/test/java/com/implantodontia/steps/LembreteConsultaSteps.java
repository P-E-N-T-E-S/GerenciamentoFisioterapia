package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LembreteConsultaSteps extends FuncionalidadesSistema {

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
        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                new Paciente(
                        new PacienteId(0),
                        new Cpf("684.976.720-89"),
                        new Endereco("Residência", "Rua dos bobos", "0", null, "Recife", "52071321"),
                        "Paciente Teste",
                        "0000-0000",
                        "Dr. Teste"
                ),
                LocalDate.now().plusDays(10),
                false,
                "Consulta Urgente",
                materiais,
                new Endereco(local, "Avenida Principal", "100", "Sala 2", "Cidade", "00000000")
        );
        agenda.put("1", consulta);
    }

    // ==================================================================
    // Cenário: Receber lembrete com 1 semana de antecedência
    // ==================================================================
    @Given("existe uma consulta agendada para {string} no local {string} com 1 semana de antecedência")
    public void criarConsulta1Semana(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                new Paciente(
                        new PacienteId(0),
                        new Cpf("684.976.720-89"),
                        new Endereco("Residência", "Rua dos bobos", "0", null, "Recife", "52071321"),
                        "Paciente Teste",
                        "0000-0000",
                        "Dr. Teste"
                ),
                LocalDate.now().plusDays(10),
                true,
                "Consulta de Rotina",
                materiais,
                new Endereco(local, "Rua Central", "200", "Consultório 5", "Cidade", "00000000")
        );
        agenda.put("2", consulta);
    }

    @Given("existe uma consulta agendada para {string} no local {string} sem lembrete agendado")
    public void criarConsultaSemLembrete(String dataHoraStr, String local) {
        agenda = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));

        Consulta consulta = new Consulta(
                null,
                dataHora,
                new Paciente(
                        new PacienteId(0),
                        new Cpf("684.976.720-89"),
                        new Endereco("Residência", "Rua dos bobos", "0", null, "Recife", "52071321"),
                        "Paciente Teste",
                        "0000-0000",
                        "Dr. Teste"
                ),
                LocalDate.now().plusDays(10),
                true,
                "Consulta sem Lembrete",
                materiais,
                new Endereco(local, "Rua Deserta", "123", null, "Cidade", "00000000")
        );
        agenda.put("3", consulta);
    }

    // ==================================================================
    // Etapa de Ação: Acessar o sistema em uma data específica
    // ==================================================================
    @When("eu acesso o sistema no dia {string}")
    public void acessarSistema(String dataAcessoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataAcesso = LocalDate.parse(dataAcessoStr, formatter);
        lembretes = new HashMap<>();

        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            String id = entry.getKey();
            Consulta consulta = entry.getValue();

            long diasEntre = java.time.temporal.ChronoUnit.DAYS.between(dataAcesso, consulta.getDataHora().toLocalDate());

            if (diasEntre == 1 || diasEntre == 7) {
                String mensagem = String.format(
                        "Lembrete: Consulta '%s' agendada para %s às %s em %s",
                        consulta.getDescricao(),
                        consulta.getDataHora().toLocalDate().format(dataFormatter),
                        consulta.getDataHora().toLocalTime().format(horaFormatter),
                        consulta.getLocal().getNome() // usamos o campo `rua` como local
                );
                lembretes.put(id, mensagem);
            }
        }
    }

    // ==================================================================
    // Etapa de Verificação - 1 dia de antecedência
    // ==================================================================
    @Then("devo ver um lembrete com:")
    public void verificarLembrete1Dia(io.cucumber.datatable.DataTable dataTable) {
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

    // ==================================================================
    // Etapa de Verificação - 1 semana de antecedência
    // ==================================================================
    @Then("devo receber um lembrete com:")
    public void verificarLembrete1Semana(io.cucumber.datatable.DataTable dataTable) {
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

    // ==================================================================
    // Etapa de Verificação - Sem lembrete
    // ==================================================================
    @Then("nenhum lembrete deve ser gerado")
    public void verificarAusenciaLembrete() {
        assertTrue(lembretes.isEmpty());
    }
}
