package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import io.cucumber.java.en.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FichaMedicaSteps {

    private PacienteId pacienteId;
    private FichaMedica fichaMedica;
    private FichaMedicaServico fichaMedicas;
    private PacienteService servico = new PacienteService();
    private boolean pdfSimulado;
    private boolean alertaAtivado;

    //==============================================================
    // Cenário 1: Gerar arquivo da ficha médica
    //==============================================================

    @Given("que existe um paciente com ficha médica no sistema")
    public void criarPacienteComFicha() {
        Paciente paciente = criarPacienteExemplo();
        this.pacienteId = paciente.getPacienteId();
        // this.fichaMedica = new FichaMedica(pacienteId);
        preencherDadosFicha();

        paciente.vincularFichaMedica(fichaMedica);
        servico.cadastrarPaciente(paciente, true);
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
        assertTrue(fichaMedicas.validarDadosObrigatorios(fichaMedica),
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
        fichaMedicas.adicionarObservacao(fichaMedica, "Nova observação de teste - " + System.currentTimeMillis());
        assertFalse(fichaMedica.getObservacoes().isEmpty(),
                "Observações deveriam estar preenchidas após atualização");
    }

    //==============================================================
    // Métodos auxiliares
    //==============================================================

    private Paciente criarPacienteExemplo() {
        Paciente paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "081 99999-99999", "Dra Katia");
        return paciente;
    }

    private void preencherDadosFicha() {
        fichaMedicas.preencherDadosClinicos(fichaMedica,
                "Histórico médico de exemplo",
                "Nenhuma alergia conhecida",
                LocalDateTime.now()
        );
    }
}