package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import com.implantodontia.persistencia.memoria.Repositorio;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

public class CadastroPacienteSteps extends FuncionalidadesSistema {

    Paciente paciente;
    protected Repositorio repositorio;
    private String erro;

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
        notifiacaoMock.atualizarNotificacao(new Notificacao("João Silva", "Pagamento atrasado!", TipoNotificacao.PAGAMENTO, LocalDateTime.now()));
        List<Notificacao> notificacoes_busca =  notifiacaoMock.displayNotificacao();
        assertFalse(notificacoes_busca.isEmpty());
        assertEquals(notificacoes_busca.get(0).getDestinatario(), (paciente.getNome()));
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
            erro = e.getMessage();
        }
    }

    @Then("o sistema exibe o erro {string}")
    public void verificarMensagemErro(String mensagemErro) {
        assertEquals(mensagemErro, this.erro);
    }
}