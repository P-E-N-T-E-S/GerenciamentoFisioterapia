package com.implantodontia.steps;

import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.persistencia.memoria.Repositorio;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;
import java.util.List;

public class MaterialSteps {

    private MaterialServico materialServico;
    private Repositorio repositorio; // Nosso repositório em memória
    private NotificacaoService notificacaoService;

    @Before
    public void setUp() {
        this.repositorio = new Repositorio();

        this.notificacaoService = new NotificacaoService(this.repositorio);

        this.notificacaoService.registrarObserver(this.repositorio);

        this.materialServico = new MaterialServico(this.repositorio, this.notificacaoService);

    }

    // ==================================================================
    // Cenário: Cadastrando um material já presente no sistema
    // ==================================================================

    @Given("eu já possuo o material {string} registrado no sistema com {int} unidades")
    public void que_eu_tenha_um_material_registrado(String item, int quantidade) {
        materialServico.adicionar(item, quantidade);
    }

    @When("eu registrar {string} com uma quantidade de {int}")
    public void eu_registrar_material_com_uma_quantidade(String item, int quantidadeAdicional) {
        materialServico.adicionar(item, quantidadeAdicional);
    }

    @Then("Eu deverei ter um total de {int} unidades de {string}")
    public void materiais_devem_ser_adicionados(int quantidadeEsperada, String item) {
        Material materialNoEstoque = materialServico.buscarPorNome(item);
        assertNotNull("Material '" + item + "' não encontrado.", materialNoEstoque);
        assertEquals("Quantidade incorreta para o material '" + item + "'.",
                quantidadeEsperada, materialNoEstoque.getQuantidade());
    }

    // ==================================================================
    // Cenário: Utilização do material
    // ==================================================================

    @Given("Uma consulta foi registrada no sistema")
    public void uma_consulta_foi_registrada_no_sistema() {
    }

    @Given("eu tenha {int} unidades do material {string} registrado")
    public void eu_tenha_unidades_do_material_registrado(int quantidade, String material) {
        materialServico.adicionar(material, quantidade);
    }

    @When("eu informar que foram precisas {int} unidades de {string} para a consulta")
    public void eu_informar_que_foram_precisas_unidades_de_para_a_consulta(int quantidadeUsada, String material) {
        materialServico.remover(material, quantidadeUsada);
    }

    @Then("o sistema deverá retornar que ainda existem {int} {string} no estoque")
    public void o_sistema_deverá_retornar_que_ainda_existem_no_estoque(int quantidadeRestanteEsperada, String item) {
        Material materialNoEstoque = materialServico.buscarPorNome(item);
        assertNotNull("Material '" + item + "' não encontrado após uso.", materialNoEstoque);
        assertEquals("Quantidade restante incorreta para o material '" + item + "'.",
                quantidadeRestanteEsperada, materialNoEstoque.getQuantidade());
    }

    // ==================================================================
    // Cenário: Aviso de Esvaimento
    // ==================================================================

    @Given("eu tenho apenas {int} {string} no sistema")
    public void eu_tenho_apenas_material_no_sistema(int quantidade, String item) {
        Material materialExistente = materialServico.buscarPorNome(item);
        if (materialExistente != null) {
            try {
                materialServico.remover(item, materialExistente.getQuantidade());
            } catch (Exception e) {  }
        }
        materialServico.adicionar(item, quantidade);
    }

    @When("eu informar que utilizei {int} {string} em minha ultima consulta")
    public void eu_informar_que_utilizei_material_na_consulta(int quantidadeUtilizada, String item) {
        materialServico.remover(item, quantidadeUtilizada);
    }

    @Then("devo ser alertado que eu preciso adquirir mais {string}")
    public void devo_ser_alertado_que_eu_preciso_adquirir_mais(String item) {
        List<Notificacao> notificacoesRecebidas = repositorio.displayNotificacao();

        assertNotNull("A lista de notificações não deveria ser nula.", notificacoesRecebidas);
        assertFalse("Nenhuma notificação foi gerada, mas uma era esperada.", notificacoesRecebidas.isEmpty());

        String mensagemEsperada = "preciso adquirir mais " + item;
        boolean notificacaoEncontrada = false;
        for (Notificacao notificacao : notificacoesRecebidas) {
            if (notificacao.getMensagem().equals(mensagemEsperada)) {
                notificacaoEncontrada = true;
                break;
            }
        }
        assertTrue("A notificação esperada sobre '" + item + "' ('" + mensagemEsperada + "') não foi encontrada. Notificações recebidas: " + notificacoesRecebidas, notificacaoEncontrada);
    }
}