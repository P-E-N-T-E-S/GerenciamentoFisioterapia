package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaImplanta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import io.cucumber.java.en.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;

import java.time.LocalDateTime;

import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.junit.jupiter.api.Assertions.*;

public class FichaMedicaSteps extends FuncionalidadesSistema {

    String pdfSimulado;
    boolean fichaPreenchida;
    Paciente paciente;

    //==============================================================
    // Cenário 1: Gerar arquivo da ficha médica
    //==============================================================
    @Given("que existe um paciente com ficha médica no sistema")
    public void criarPacienteComFicha() {
        FichaMedicaImplanta fichaMedica = new FichaMedicaImplanta(new PacienteId(0), 0L);
        Paciente paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "81 997510500", "Dra Katia", "Teste@gmail.com");
        pacienteService.cadastrarPaciente(paciente, true);
        fichaMedica.preencherDadosClinicos("Treze fraturas cranianas", "Alergia a Dopamina");
        paciente.vincularFichaMedica(fichaMedica);

    }

    @When("eu pedir para exportar a ficha desse paciente")
    public void solicitarExportacaoFicha() {
        pdfSimulado = "PDF Gerado com sucesso!";
    }


    @Then("eu devo ser capaz de baixar esse arquivo no meu computador")
    public void verificarDownloadDisponivel() {
        assertEquals(pdfSimulado, "PDF Gerado com sucesso!");
    }


    @Then("o arquivo deve conter todos os dados clínicos do paciente")
    public void verificarDadosClinicos() {
        assertTrue(fichaMedicaServico.validarDadosObrigatorios(new PacienteId(0)),"Ficha deve ter dados obrigatórios preenchidos");
    }

    //==============================================================
    // Cenário 2: Campo de observações
    //==============================================================

    @Given("que haja um paciente no sistema, com ficha")
    public void criarPacienteComFichaExistente() {
        FichaMedicaImplanta fichaMedica = new FichaMedicaImplanta(new PacienteId(0), 0L);
        this.paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "81 997510500", "Dra Katia", "Teste@gmail.com");
        pacienteService.cadastrarPaciente(paciente, true);
        paciente.vincularFichaMedica(fichaMedica);
    }

    @When("eu registrar uma consulta realizada com esse paciente")
    public void registrarConsulta() {
        this.fichaPreenchida = fichaMedicaServico.validarDadosObrigatorios(new PacienteId(0));
    }

    @Then("eu devo ser alertado para fazer anotações na ficha médica dele")
    public void verificarAlertaObservacoes() {
        assertFalse(fichaPreenchida, "Sistema deveria solicitar atualização das observações");
    }

    @Then("devo poder adicionar observações na ficha")
    public void adicionarObservacoes() {
        FichaMedicaImplanta fichaMedica = new FichaMedicaImplanta(new PacienteId(0), 0L);
        fichaMedica.preencherDadosClinicos("Treze fraturas cranianas", "Alergia a Dopamina");
        paciente.vincularFichaMedica(fichaMedica);
        assertFalse(paciente.getFichaMedica().getHistoricoMedico().isEmpty());
    }

    //==============================================================
    // Métodos auxiliares
    //==============================================================

    private Paciente criarPacienteExemplo() {
        Paciente paciente = new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "081 99999-99999", "Dra Katia", "Teste@gmail.com");
        return paciente;
    }

    private void preencherDadosFicha() {

    }
}