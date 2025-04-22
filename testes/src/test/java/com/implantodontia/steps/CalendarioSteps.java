package com.implantodontia.steps;

import com.implantodontia.dominio.support.relatorio.Calendario;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarioSteps {
    private Map<String, Consulta> agenda = new HashMap<>();
    Calendario calendario = new Calendario();

    @Given("que eu tenha 3 consultas agendadas nos dias 10, 17 e 28 de Abril de 2025")
    public void mapearConsultasSistema(){
        agenda = new HashMap<>();

        Consulta consultaBase = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 8),
                "Clínica Central", new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Roger", "(81) 9999-99999", "Dra Katia"));

        Consulta consulta2 = new Consulta(
                LocalDateTime.of(2025, 4, 17, 0, 0),
                "Consulta B",
                new Material(),
                true,
                LocalDate.of(2025, 4, 10),
                "Hospital Geral",
                new Paciente(new PacienteId(1), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Marcos", "(81) 9999-99999", "Dra Roberta"));

        Consulta consulta3 = new Consulta(
                LocalDateTime.of(2025, 4, 28, 0, 0),
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

    @When("eu acessar o meu calendário de consultas")
    public void acessandoCalendario(){

    }

    @Then("o sistema deve exibir essas 3 consultas nos seus respectivos dias no calendário de Abril de 2025")
    public void validaoCalendario(){
        ArrayList<ArrayList<Consulta>> lista = calendario.setupCalendario(agenda);

        assertEquals(3, lista.get(3).size());

        assertTrue(lista.get(3).stream()
                .anyMatch(consulta -> consulta.getDataHora().getDayOfMonth() == 10));
        assertTrue(lista.get(3).stream()
                .anyMatch(consulta -> consulta.getDataHora().getDayOfMonth() == 17));
        assertTrue(lista.get(3).stream()
                .anyMatch(consulta -> consulta.getDataHora().getDayOfMonth() == 28));
    }
}
