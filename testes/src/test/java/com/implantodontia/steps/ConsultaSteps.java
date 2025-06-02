package com.implantodontia.steps;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import io.cucumber.java.en.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                "Clínica Central", new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "0", null, "Recife", "52071321"), "Roger", "(81) 9999-99999", "Dra Katia"));

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
