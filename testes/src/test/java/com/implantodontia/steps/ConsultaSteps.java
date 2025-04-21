package com.implantodontia.steps;
import com.implantodontia.dominio.consulta.Consulta;
import com.implantodontia.dominio.consulta.Material;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import io.cucumber.java.en.*;
import com.implantodontia.dominio.consulta.Procedimento;
import com.implantodontia.dominio.consulta.services.HistoricoProcedimentosService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaSteps {
    private final Map<String, Consulta> agenda = new HashMap<>();

    @Given("que eu tenha uma consulta realizada, mas não paga na data atual")
    public void criandoConsultaNaoPaga() {
        Consulta consultaBase = new Consulta(
                LocalDateTime.of(2025, 4, 10, 0, 0),
                "Consulta A",
                new Material(),
                false,
                LocalDate.of(2025, 4, 8),
                "Clínica Central", new Paciente("Roger", "(81) 9999-99999", "Dra Katia"));

                agenda.put("1", consultaBase);
    }

    @When("eu acessar o sistema em um dado dia")
    public void quando_eu_acessar_o_sistema(){
        LocalDate hoje = LocalDate.of(2025, 4, 12);
    }

    @Then("eu devo ser avisado de que existem pendencias de pagamento")
    public void avisoPagamentoPendente(){
        LocalDate hoje = LocalDate.of(2025, 4, 12);
        Map<String, String> notificacoes = Consulta.gerarNotificacoesPendencias(agenda, hoje);
        assertEquals(1, notificacoes.size());
        assertTrue(notificacoes.get("1").contains("atrasado há 4 dias"));
    }
}
