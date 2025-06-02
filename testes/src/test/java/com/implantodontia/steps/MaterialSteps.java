//package com.implantodontia.steps;
//
//import io.cucumber.java.en.*;
//
//import static org.junit.Assert.*;
//
//import java.util.Map;
//
//import com.implantodontia.dominio.core.material.MaterialServico;
//
//import java.util.HashMap;
//
//// História N°1
//public class MaterialSteps {
//
//    private final Map<String, Integer> inventario = new HashMap<>();
//    private MaterialServico servico = new MaterialServico();
//
//    // ==================================================================
//    // Cenário: Adicionar material existente
//    // ==================================================================
//
//    @Given("eu já possuo o material {string} registrado no sistema com {int} unidades")
//    public void que_eu_tenha_um_material_registrado(String item, int quantidade) {
//        servico.adicionar(inventario, item, quantidade);
//    }
//
//    @When("eu registrar {string} com uma quantidade de {int}")
//    public void eu_registrar_material_com_uma_quantidade(String item, int quantidade) {
//        servico.adicionar(inventario, item, quantidade);
//    }
//
//    @Then("Eu deverei ter um total de {int} unidades de {string}")
//    public void materiais_devem_ser_adicionados(int quantidade, String item) {
//        assertEquals((Integer) quantidade, inventario.get(item));
//    }
//
//    // ==================================================================
//    // Cenário: Uso dos materiais
//    // ==================================================================
//
//
//    @Given("Uma consulta foi registrada no sistema")
//    public void uma_consulta_foi_registrada_no_sistema() {
//        // Stub: apenas simula o registro de uma consulta
//    }
//
//    @And("eu tenha {int} unidades do material {string} registrado")
//    public void eu_tenha_unidades_do_material_registrado(int quantidade, String material) {
//        servico.adicionar(inventario, material, quantidade);
//    }
//
//    @When("eu informar que foram precisas {int} unidades de {string} para a consulta")
//    public void eu_informar_que_foram_precisas_unidades_de_para_a_consulta(int quantidade, String material) {
//        servico.remover(inventario, material, quantidade);
//    }
//
//    @Then("o sistema deverá retornar que ainda existem {int} {string} no estoque")
//    public void o_sistema_deverá_retornar_que_ainda_existem_no_estoque(int quantidade, String item) {
//        assertEquals((Integer) quantidade, inventario.get(item));
//    }
//
//    // ==================================================================
//    // Cenário: Aviso de esvaimento
//    // ==================================================================
//
//    @Given("eu tenho apenas {int} {string} no sistema")
//    public void eu_tenho_apenas_material_no_sistema(int quantidade, String item) {
//        servico.adicionar(inventario, item, quantidade);
//    }
//
//    @When("eu informar que utilizei {int} {string} em minha ultima consulta")
//    public void eu_informar_que_utilizei_material_na_consulta(int quantidade, String item) {
//          servico.remover(inventario, item, quantidade); // ou servico.subtrair, depende de como você nomeou
//    }
//
//    @Then("devo ser alertado que eu preciso adquirir mais {string}")
//    public void devo_ser_alertado_que_eu_preciso_adquirir_mais(String item) {
//        String retorno = servico.remover(inventario, item, 0);
//        assertEquals(("preciso adquirir mais " + item), retorno);
//    }
//}
