package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CadastroPacienteSteps {

    private ServicoPacientes servico = new ServicoPacientes();
    private Paciente paciente;
    private String erro;

    // ==================================================================
    // Cenário 1: Cadastro válido
    // ==================================================================
    @Given("o sistema está preparado para cadastros")
    public void prepararSistema() {
        servico.getPacientes().clear();
        servico.getNotificacoes().clear();
    }

    @When("cadastro um novo paciente válido com nome {string}, contato {string} e médico {string}")
    public void cadastrarPacienteValido(String nome, String contato, String medico) {
        this.paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), nome, contato, medico);
        servico.cadastrarPaciente(paciente);
    }

    @Then("o paciente é adicionado à lista de pacientes")
    public void verificarPacienteNaLista() {
        List<Paciente> pacientes = servico.getPacientes();
        assertTrue(pacientes.contains(paciente));
    }

    @Then("uma notificação é gerada para a fisioterapeuta")
    public void verificarNotificacao() {
        List<String> notificacoes = servico.getNotificacoes();
        assertFalse(notificacoes.isEmpty());
        assertTrue(notificacoes.get(0).contains(paciente.getNome()));
    }

    // ==================================================================
    // Cenário 2: Cadastro inválido
    // ==================================================================
    @When("tento cadastrar um paciente inválido com nome {string}, contato {string} e médico {string}")
    public void cadastrarPacienteInvalido(String nome, String contato, String medico) {
        try {
            this.paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), nome, contato, medico);
            servico.cadastrarPaciente(paciente);
        } catch (IllegalArgumentException e) {
            this.erro = e.getMessage();
        }
    }

    @Then("o sistema exibe o erro {string}")
    public void verificarMensagemErro(String mensagemErro) {
        assertEquals(mensagemErro, this.erro);
    }
}