package com.implantodontia.steps;

import io.cucumber.java.en.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FichaMedicaSteps {

    private PacienteId pacienteId;
    private FichaMedica fichaMedica;
    private ServicoPacientes servico;
    private boolean pdfSimulado;
    private boolean alertaAtivado;

    //==============================================================
    // Cenário 1: Gerar arquivo da ficha médica
    //==============================================================

    @Given("que existe um paciente com ficha médica no sistema")
    public void criarPacienteComFicha() {
        servico = ServicoPacientes.getInstance();

        Paciente paciente = criarPacienteExemplo();
        this.pacienteId = paciente.getPacienteId();
        this.fichaMedica = new FichaMedica(pacienteId);
        preencherDadosFicha();

        paciente.vincularFichaMedica(fichaMedica);
        servico.cadastrarPaciente(paciente);
    }

    @When("eu pedir para exportar a ficha desse paciente")
    public void solicitarExportacaoFicha() {

        this.pdfSimulado = servico.simularGeracaoPdf(pacienteId);
    }


    @Then("eu devo ser capaz de baixar esse arquivo no meu computador")
    public void verificarDownloadDisponivel() {
        assertTrue(pdfSimulado, "Deveria simular a geração do PDF com sucesso");
    }


    @Then("o arquivo deve conter todos os dados clínicos do paciente")
    public void verificarDadosClinicos() {
        assertTrue(fichaMedica.validarDadosObrigatorios(),
                "Ficha deve ter dados obrigatórios preenchidos");
    }

    //==============================================================
    // Cenário 2: Campo de observações
    //==============================================================

    @Given("que haja um paciente no sistema, com ficha")
    public void criarPacienteComFichaExistente() {
        criarPacienteComFicha(); // Reusa o setup do primeiro cenário
    }

    @When("eu registrar uma consulta realizada com esse paciente")
    public void registrarConsulta() {
        this.alertaAtivado = servico.deveSolicitarObservacoes(pacienteId);
    }

    @Then("eu devo ser alertado para fazer anotações na ficha médica dele")
    public void verificarAlertaObservacoes() {
        assertTrue(alertaAtivado, "Sistema deveria solicitar atualização das observações");
    }

    @Then("devo poder adicionar observações na ficha")
    public void adicionarObservacoes() {
        fichaMedica.adicionarObservacao("Nova observação de teste - " + System.currentTimeMillis());
        assertFalse(fichaMedica.getObservacoes().isEmpty(),
                "Observações deveriam estar preenchidas após atualização");
    }

    //==============================================================
    // Métodos auxiliares
    //==============================================================

    private Paciente criarPacienteExemplo() {
        return new Paciente(
                new PacienteId(0),
                new Cpf("111.222.333-44"),
                new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Roger", "(81) 9999-99999", "Dra Katia");
    }

    private void preencherDadosFicha() {
        fichaMedica.preencherDadosClinicos(
                "Histórico médico de exemplo",
                "Nenhuma alergia conhecida",
                LocalDateTime.now()
        );
    }
}