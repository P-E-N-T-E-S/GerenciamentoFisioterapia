/*package teste;


import com.implantodontia.dominio.consulta.services.ServicoPacientes;
import com.implantodontia.dominio.consulta.paciente.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ServicoPacientesTest {
    private ServicoPacientes servico;


    @BeforeEach
    void setUp() {
        servico = ServicoPacientes.getInstance();
        servico.limparNotificacoes();
    }

    @Test
    void deveGerarNotificacaoAoCadastrarPaciente() {
        Paciente paciente = new Paciente(
                "João Silva",
                "joao@email.com",
                "Dr. Carlos"
        );

        servico.cadastrarPaciente(paciente);

        List<String> notificacoes = servico.getNotificacoes();
        assertEquals(1, notificacoes.size());
        assertTrue(notificacoes.get(0).contains("Novo paciente cadastrado: João Silva"));
        assertTrue(notificacoes.get(0).contains("por Dr. Carlos"));
        assertTrue(notificacoes.get(0).contains("joao@email.com"));
    }

    @Test
    void deveManterHistoricoDeNotificacoes() {
        servico.cadastrarPaciente(new Paciente("Maria", "maria@email.com", "Dra. Ana"));
        servico.cadastrarPaciente(new Paciente("Pedro", "pedro@email.com", "Dr. Lucas"));

        assertEquals(2, servico.getNotificacoes().size());
    }
}*/