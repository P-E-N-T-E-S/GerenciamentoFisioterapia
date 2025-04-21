package com.implantodontia.steps;

import com.implantodontia.dominio.consulta.Material;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.services.NotificacaoPagamentoPacienteServico;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class NotificacaoPagamentoPendenteSteps {
    final NotificacaoPagamentoPacienteServico servico =  new NotificacaoPagamentoPacienteServico();
    private Map<String, Consulta> agenda = new HashMap<>();

    @Given("que eu sou um paciente e realizei uma consulta com a Dra.")
    public void realizarConsulta() {
       Consulta consulta3 = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 17),
                "Clínica Central", new Paciente("Roger", "(81) 9999-99999", "Dra Katia"));
                agenda.put("1", consulta3);
    }

    @When("a data atual for um dia antes da data de vencimento do pagamento")
    public void dataAtualUmDiaAnterior() {
        // Data atual deve ser um dia antes de 17/04/2025 (16/04/2025)
        LocalDate dataAtual = LocalDate.of(2025, 4, 16);
        // Verificação de que a notificação foi gerada corretamente
    }

    @Then("devo ser alertado que o pagamento vencerá amanhã")
    public void alertaVencimentoAmanha() {
        LocalDate dataAtual = LocalDate.of(2025, 4, 16);
        String validacao = servico.verificarPagamentoPendenteDia(
                new Paciente("Roger", "(81) 9999-99999", "Dra Katia"), agenda, dataAtual);
        assertEquals("Pagamento pendente do paciente: Roger - para o dia 2025-04-17", validacao);
    }

    @Given("que eu sou um paciente e realizei uma consulta com a Dra")
    public void realizar_a_consulta() {
        Consulta consulta3 = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 17),
                "Clínica Central", new Paciente("Roger", "(81) 9999-99999", "Dra Katia"));
        agenda.put("1", consulta3);
    }

    // Step 2: Data atual é 1 dia anterior à data de vencimento
    @When("a data atual for uma semana antes da data de vencimento do pagamento")
    public void dataAtualUmaSemanaAnterior() {
        // Data atual deve ser um dia antes de 17/04/2025 (16/04/2025)
        LocalDate dataAtual = LocalDate.of(2025, 4, 16);
        // Verificação de que a notificação foi gerada corretamente
    }

    // Step 3: O paciente será alertado sobre o pagamento
    @Then("devo ser alertado que o pagamento vencerá em uma semana")
    public void alertaVencimentoProximaSemana() {
        LocalDate dataAtual = LocalDate.of(2025, 4, 16);
        String validacao = servico.verificarPagamentoPendenteSemana(
                new Paciente("Roger", "(81) 9999-99999", "Dra Katia"), agenda, dataAtual);
        assertEquals("Pagamento pendente do paciente: Roger - para o dia 2025-04-17", validacao);
    }
}
