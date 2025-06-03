package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.support.relatorio.Calendario;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioSteps extends FuncionalidadesSistema {
    private Map<String, Consulta> agenda = new HashMap<>();
    private List<Material> materiais = new ArrayList<>();
    Calendario calendario = new Calendario();

    @Given("que eu tenha 3 consultas agendadas nos dias 10, 17 e 28 de Abril de 2025")
    public void mapearConsultasSistema(){
        materialServico.adicionar("Gaze", 2);
        materialServico.adicionar("Tape", 2);
        materialServico.adicionar("Fita", 2);

        materiais.add(new Material(1, "Gaze"));
        materiais.add(new Material(1, "Tape"));
        materiais.add(new Material(1, "Fita"));

        Consulta consultaBase = new Consulta(
                new ConsultaId(0L),
                LocalDateTime.of(2025, 4, 17, 0, 0),
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "081 99999-99999", "Dra Katia"),
                LocalDate.of(2025, 4, 21),
                false,
                "Consulta B",
                materiais,
                new Endereco("Rua dos bobos", "Arruda", "1", "Clinica Geral", "Recife", "52071321"),
                1000.0);

        Consulta consulta2 = new Consulta(
                new ConsultaId(1L),
                LocalDateTime.of(2025, 4, 21, 0, 0),
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "Jeremias Silva", "081 99999-99999", "Dra Katia"),
                LocalDate.of(2025, 4, 30),
                false,
                "Consulta B",
                materiais,
                new Endereco("Rua dos bobos", "Arruda", "1", "Clinica Geral", "Recife", "52071321"),
                1000.0);

        Consulta consulta3 = new Consulta(
                new ConsultaId(2L),
                LocalDateTime.of(2025, 4, 30, 0, 0),
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "Evaldo Galdino", "081 99999-99999", "Dra Katia"),
                LocalDate.of(2025, 5, 7),
                false,
                "Consulta B",
                materiais,
                new Endereco("Rua dos bobos", "Arruda", "1", "Clinica Geral", "Recife", "52071321"),
                1000.0);

        consultaService.salvar(consultaBase);
        consultaService.salvar(consulta2);
        consultaService.salvar(consulta3);
    }

    @When("eu acessar o meu calendário de consultas")
    public void acessandoCalendario(){

    }

    @Then("o sistema deve exibir essas 3 consultas nos seus respectivos dias no calendário de Abril de 2025")
    public void validaoCalendario(){
        assertEquals(3, consultaService.listarConsultas().size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        assertEquals("17/04/2025", consultaService.listarConsultas().get(0).getDataHora().format(formatter));
        assertEquals("21/04/2025", consultaService.listarConsultas().get(1).getDataHora().format(formatter));
        assertEquals("30/04/2025", consultaService.listarConsultas().get(2).getDataHora().format(formatter));
    }
}
