package com.implantodontia.steps;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.persistencia.memoria.FuncionalidadesSistema;
import com.implantodontia.persistencia.memoria.NotifiacaoMock;
import io.cucumber.java.en.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaSteps extends FuncionalidadesSistema {
    private List<Material> materiais = new ArrayList<>();



    @Given("que eu tenha uma consulta realizada, mas não paga na data atual")
    public void criandoConsultaNaoPaga() {
        materiais.add(new Material(2, "Gaze"));
        materiais.add(new Material(2, "Tape"));
        materiais.add(new Material(2, "Fita"));

        Consulta consultaBase = new Consulta(
                new ConsultaId(0L),
                LocalDateTime.of(2025, 4, 17, 0, 0),
                new Paciente(new PacienteId(0), new Cpf("684.976.720-89"), new Endereco("Rua dos bobos", "Arruda", "0", "Casa", "Recife", "52071321"), "José Valder", "081 99999-99999", "Dra Katia"),
                LocalDate.of(2025, 4, 21),
                false,
                "Consulta B",
                materiais,
                new Endereco("Rua dos bobos", "Arruda", "1", "Clinica Geral", "Recife", "52071321"),
                1000.0);
        notifiacaoMock.atualizarNotificacao(new Notificacao("José Valder", "atrasado há 4 dias", TipoNotificacao.PAGAMENTO, LocalDateTime.now()));
    }

    @When("eu acessar o sistema em um dado dia")
    public void quando_eu_acessar_o_sistema(){
        LocalDate hoje = LocalDate.of(2025, 4, 12);
    }

    @Then("eu devo ser avisado de que existem pendencias de pagamento")
    public void avisoPagamentoPendente(){
        LocalDate hoje = LocalDate.of(2025, 4, 12);
        List<Notificacao> notificacoes = notifiacaoMock.displayNotificacao();
        assertEquals(1, notificacoes.size());
        assertEquals(notificacoes.get(0).getMensagem(), ("atrasado há 4 dias"));

    }
}
