package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaRepository;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Cpf;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialRepository;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.persistencia.memoria.Repositorio;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

public class LembreteConsultaSteps {

    private Map<String, String> lembretes = new HashMap<>();
    private LocalDate dataDeHoje;

    private ConsultaService consultaService;
    private Repositorio repositorio;
    private MaterialServico materialServico;
    private NotificacaoService notificacaoService;

    private static final AtomicLong proximoConsultaIdGenerator = new AtomicLong(1L);
    private static final AtomicLong proximoPacienteIdGenerator = new AtomicLong(100L);

    public LembreteConsultaSteps() {
        this.repositorio = new Repositorio();

        MaterialRepository materialRepository = this.repositorio;
        ConsultaRepository consultaRepository = this.repositorio;
        NotificacaoConsumidor notificacaoConsumidor = this.repositorio;

        this.notificacaoService = new NotificacaoService(notificacaoConsumidor);

        this.materialServico = new MaterialServico(materialRepository, this.notificacaoService);

        this.consultaService = new ConsultaService(consultaRepository, this.materialServico);
    }

    @Before
    public void setUp() {
        this.repositorio.limparDadosDeTeste();
        this.lembretes.clear();
        this.dataDeHoje = null;
        proximoConsultaIdGenerator.set(1L);
        proximoPacienteIdGenerator.set(100L);
    }

    private void gerarLembretes() {
        lembretes.clear();
        List<Consulta> consultasAgendadas = consultaService.listarConsultas();

        for (Consulta consulta : consultasAgendadas) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(dataDeHoje, consulta.getDataHora().toLocalDate());
            if (dias == 1 || dias == 7) {
                String data = consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String hora = consulta.getDataHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                String local = consulta.getLocal().getNome();
                String descricao = consulta.getDescricao();

                String mensagem = String.format("Lembrete: Consulta '%s' agendada para %s às %s em %s",
                        descricao, data, hora, local);
                if (consulta.getConsultaId() != null && consulta.getConsultaId().getId() != null) {
                    lembretes.put(String.valueOf(consulta.getConsultaId().getId()), mensagem);
                }
            }
        }
    }
    private Paciente criarPacienteParaTeste() {
        PacienteId pacienteId = new PacienteId(proximoPacienteIdGenerator.getAndIncrement());
        Paciente paciente = new Paciente(
                pacienteId,
                new Cpf("123.456.789-00"),
                new Endereco("Endereço Teste", "Rua Teste", "123", "Apto 1", "TesteCidade", "00000-000"),
                "Paciente de Teste " + pacienteId.getId(),
                "99999-9999",
                "Dr. Testador"
        );
        return paciente;
    }
    private List<Material> criarListaDeMateriaisParaTeste() {
        List<Material> materiais = new ArrayList<>();
        materiais.add(new Material(1, "Gaze"));
        return materiais;
    }


    private ConsultaId gerarProximoConsultaId() {
        return new ConsultaId(proximoConsultaIdGenerator.getAndIncrement());
    }


    @Given("existe uma consulta agendada para {string} no local {string} com 1 dia de antecedência")
    public void consultaCom1DiaDeAntecedencia(String dataHoraStr, String local) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                gerarProximoConsultaId(),
                dataHora,
                criarPacienteParaTeste(),
                LocalDate.now().plusDays(10),
                false,
                "Consulta Urgente",
                criarListaDeMateriaisParaTeste(),
                new Endereco(local, "Rua A", "123", "Sala 1", "Cidade Exemplo", "10000-001"),
                0.0
        );
        consultaService.salvar(consulta);
    }

    @Given("existe uma consulta agendada para {string} no local {string} com 1 semana de antecedência")
    public void consultaCom1SemanaDeAntecedencia(String dataHoraStr, String local) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                gerarProximoConsultaId(),
                dataHora,
                criarPacienteParaTeste(),
                LocalDate.now().plusDays(10),
                true,
                "Consulta de Rotina",
                criarListaDeMateriaisParaTeste(),
                new Endereco(local, "Rua B", "456", "Sala 2", "Cidade Exemplo", "20000-002"),
                50.0
        );
        consultaService.salvar(consulta);
    }

    @Given("existe uma consulta agendada para {string} no local {string} sem lembrete agendado")
    public void consultaSemLembrete(String dataHoraStr, String local) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);

        Consulta consulta = new Consulta(
                gerarProximoConsultaId(),
                dataHora,
                criarPacienteParaTeste(),
                LocalDate.now().plusDays(10),
                true,
                "Consulta sem Lembrete",
                criarListaDeMateriaisParaTeste(),
                new Endereco(local, "Rua C", "789", "Sala 3", "Cidade Exemplo", "30000-003"),
                75.0
        );
        consultaService.salvar(consulta);
    }

    @When("eu acesso o sistema no dia {string}")
    public void acessoOSistema(String dataHojeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataDeHoje = LocalDate.parse(dataHojeStr, formatter);
        gerarLembretes();
    }

    @Then("devo ver um lembrete com:")
    public void deveVerLembrete(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta '%s' agendada para %s às %s em %s",
                "Consulta Urgente",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertTrue(lembretes.containsValue(mensagemEsperada),
                "Lembrete esperado não encontrado para 'Consulta Urgente'. Lembretes gerados: " + lembretes.values() + ". Data do sistema para lembrete: " + dataDeHoje);
    }

    @Then("devo receber um lembrete com:")
    public void deveReceberLembrete(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> dados = dataTable.asMaps().get(0);
        String mensagemEsperada = String.format(
                "Lembrete: Consulta '%s' agendada para %s às %s em %s",
                "Consulta de Rotina",
                dados.get("Data"),
                dados.get("Hora"),
                dados.get("Local")
        );
        assertEquals(1, lembretes.size(), "Deveria haver exatamente 1 lembrete para 'Consulta de Rotina'. Lembretes gerados: " + lembretes.values() + ". Data do sistema para lembrete: " + dataDeHoje);
        assertTrue(lembretes.containsValue(mensagemEsperada),
                "Lembrete esperado não encontrado para 'Consulta de Rotina'. Lembretes gerados: " + lembretes.values() + ". Data do sistema para lembrete: " + dataDeHoje);
    }

    @Then("nenhum lembrete deve ser gerado")
    public void nenhumLembreteGerado() {
        assertTrue(lembretes.isEmpty(), "Não deveria haver lembretes gerados, mas foram encontrados: " + lembretes.values() + ". Data do sistema para lembrete: " + dataDeHoje);
    }
}