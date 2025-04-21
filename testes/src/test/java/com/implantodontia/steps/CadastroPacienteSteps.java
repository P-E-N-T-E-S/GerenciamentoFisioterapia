package com.implantodontia.steps;

import io.cucumber.java.en.*;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import com.implantodontia.dominio.consulta.services.ServicoPacientes;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class CadastroPacienteSteps {

    private ServicoPacientes servico = ServicoPacientes.getInstance();
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
        this.paciente = new Paciente(nome, contato, medico);
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
            this.paciente = new Paciente(nome, contato, medico);
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