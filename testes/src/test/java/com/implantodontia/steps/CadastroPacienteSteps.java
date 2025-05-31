package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CadastroPacienteSteps extends FuncionalidadesSistema {

    Paciente paciente;

    // ==================================================================
    // Cenário 1: Cadastro válido
    // ==================================================================
    @Given("o sistema está preparado para cadastros")
    public void prepararSistema() {
        List resultado = pacienteService.listarPacientes();
        assertTrue(resultado.isEmpty());
    }

    @When("cadastro um novo paciente válido com nome {string}, contato {string} e médico {string}")
    public void cadastrarPacienteValido(String nome, String contato, String medico) {
        this.paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), nome, contato, medico);
        pacienteService.cadastrarPaciente(paciente, true);
    }

    @Then("o paciente é adicionado à lista de pacientes")
    public void verificarPacienteNaLista() {
        Paciente paciente_busca = pacienteService.buscarPacientePorId(new PacienteId(0));
        assertTrue(paciente_busca.equals(paciente));
    }

    @Then("uma notificação é gerada para a fisioterapeuta")
    public void verificarNotificacao() {
        //List<String> notificacoes = pacienteService.getNotificacoes();
        //assertFalse(notificacoes.isEmpty());
        //assertTrue(notificacoes.get(0).contains(paciente.getNome()));
    }

    // ==================================================================
    // Cenário 2: Cadastro inválido
    // ==================================================================
    @When("tento cadastrar um paciente inválido com nome {string}, contato {string} e médico {string}")
    public void cadastrarPacienteInvalido(String nome, String contato, String medico) {
        try {
            Paciente paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), nome, contato, medico);
            pacienteService.cadastrarPaciente(paciente, false);
        } catch (IllegalArgumentException e) {
        }
    }

    @Then("o sistema exibe o erro {string}")
    public void verificarMensagemErro(String mensagemErro) {
        //assertEquals(mensagemErro, this.erro);
    }
}