package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.support.notificacoes.NotificacaoPagamentoPacienteServico;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class NotificacaoPagamentoPendenteSteps {

    private NotificacaoPagamentoPacienteServico servico;
    private Map<String, Consulta> agenda;
    private Paciente pacienteTeste;
    private LocalDate dataAtualParaTeste;

    private static final AtomicLong proximoConsultaIdSteps = new AtomicLong(1L);
    private static final AtomicLong proximoPacienteIdSteps = new AtomicLong(200L);

    @Before
    public void setUp() {
        servico = new NotificacaoPagamentoPacienteServico();
        agenda = new HashMap<>();
        proximoConsultaIdSteps.set(1L);
        proximoPacienteIdSteps.set(200L);

        Endereco enderecoPaciente = new Endereco(
                "Casa do Roger",
                "Rua dos Sonhos",
                "10",
                "Apto 101",
                "FelizCidade",
                "55555-321"
        );
        pacienteTeste = new Paciente(
                new PacienteId(proximoPacienteIdSteps.getAndIncrement()),
                new Cpf("684.976.720-89"),
                enderecoPaciente,
                "Roger Steps Pagamento",
                "(81) 98888-7777",
                "Dra. Esperança",
                "Roger@Gmail.com"
        );
    }

    private ConsultaId gerarProximoConsultaIdParaSteps() {
        return new ConsultaId(proximoConsultaIdSteps.getAndIncrement());
    }

    private Consulta criarConsultaPadrao(LocalDate dataVencimentoPagamento) {
        ConsultaId consultaId = gerarProximoConsultaIdParaSteps();
        LocalDateTime dataHoraConsulta = LocalDateTime.of(2025, 4, 1, 10, 0);
        boolean clientePagou = false;
        String descricao = "Consulta de Avaliação de Pagamento";
        List<Material> materiaisDaConsulta = new ArrayList<>();
        materiaisDaConsulta.add(new Material(1, "Material Básico para Pagamento")); // Confirme este construtor
        Endereco localDaConsulta = new Endereco(
                "Clínica Saúde Já",
                "Avenida das Clínicas",
                "789",
                "Sala 7",
                "FelizCidade",
                "55555-400"
        );
        Double valorConsulta = 200.0;

        return new Consulta(
                consultaId,
                dataHoraConsulta,
                pacienteTeste,
                dataVencimentoPagamento,
                clientePagou,
                descricao,
                materiaisDaConsulta,
                localDaConsulta,
                valorConsulta
        );
    }

    @Given("que eu sou um paciente e realizei uma consulta com a Dra")
    public void realizarConsulta() {
        Consulta consulta = criarConsultaPadrao(LocalDate.of(2025, 4, 17));
        agenda.put(consulta.getConsultaId().getId().toString(), consulta);
    }

    @When("a data atual for um dia antes da data de vencimento do pagamento")
    public void dataAtualUmDiaAnterior() {
        dataAtualParaTeste = LocalDate.of(2025, 4, 16);
    }

    @Then("devo ser alertado que o pagamento vencerá amanhã")
    public void alertaVencimentoAmanha() {
        String validacao = servico.verificarPagamentoPendenteDia(pacienteTeste, agenda, dataAtualParaTeste);
        assertEquals("Pagamento pendente do paciente: Roger Steps Pagamento - para o dia 2025-04-17", validacao);
    }

    @Given("que eu sou um paciente e minha consulta tem um pagamento futuro")
    public void consultaComPagamentoFuturo() {
        Consulta consulta = criarConsultaPadrao(LocalDate.of(2025, 4, 17));
        agenda.put(consulta.getConsultaId().getId().toString(), consulta);
    }

    @When("a data atual for uma semana antes da data de vencimento do pagamento")
    public void dataAtualUmaSemanaAnterior() {
        dataAtualParaTeste = LocalDate.of(2025, 4, 10);
    }

    @Then("devo ser alertado que o pagamento vencerá em uma semana")
    public void alertaVencimentoProximaSemana() {
        String validacao = servico.verificarPagamentoPendenteSemana(pacienteTeste, agenda, dataAtualParaTeste);
        assertEquals("Pagamento pendente do paciente: Roger Steps Pagamento - para o dia 2025-04-17", validacao);
    }
}