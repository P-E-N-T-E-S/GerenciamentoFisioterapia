package com.implantodontia.steps;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.TipoProcedimento;
import com.implantodontia.dominio.support.relatorio.procedimento.HistoricoProcedimentosService;

import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoricoProcedimentosSteps {

    private final HistoricoProcedimentosService service = new HistoricoProcedimentosService();
    private List<Procedimento> resultados;

    @Given("não existem procedimentos cadastrados")
    public void nao_existem_procedimentos_cadastrados() {
        service.limparProcedimentos();
    }

    @Given("existem procedimentos cadastrados")
    public void existem_procedimentos_cadastrados() {
        // Criar procedimento 1: tipo INTRA_OPERATORIO em 29/08/2020
        Procedimento procedimento1 = new Procedimento(
                LocalDate.of(2020, 8, 29),
                TipoProcedimento.INTRA_OPERATORIO,
                "Procedimento cirúrgico",
                "Hospital Geral"
        );

        // Criar procedimento 2: tipo CONSULTA_PADRAO em 15/03/2024
        Procedimento procedimento2 = new Procedimento(
                LocalDate.of(2024, 3, 15),
                TipoProcedimento.CONSULTA_PADRAO,
                "Consulta inicial",
                "Clínica Central"
        );

        service.adicionarProcedimento(procedimento1);
        service.adicionarProcedimento(procedimento2);
    }

    @When("eu busco procedimentos na data {string}")
    public void eu_busco_procedimentos_na_data(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);
        resultados = service.filtrarPorDataExata(data);
    }

    @When("eu filtro por tipo {string}")
    public void eu_filtro_por_tipo(String tipoStr) {
        TipoProcedimento tipo = TipoProcedimento.valueOf(tipoStr);
        resultados = service.filtrarPorTipo(tipo);
    }

    @When("eu filtro pelo mês {int} e ano {int}")
    public void eu_filtro_pelo_mes_e_ano(Integer mes, Integer ano) {
        resultados = service.filtrarPorMesAno(mes, ano);
    }

    @Then("o sistema retorna uma lista vazia")
    public void o_sistema_retorna_uma_lista_vazia() {
        assertNotNull(resultados, "A lista não deve ser nula");
        assertTrue(resultados.isEmpty(), "A lista deve estar vazia");
    }

    @Then("o sistema retorna {int} procedimento")
    public void o_sistema_retorna_procedimento(Integer quantidade) {
        assertNotNull(resultados, "A lista não deve ser nula");
        assertEquals(quantidade.intValue(), resultados.size(), "Número de procedimentos incorreto");
    }

    @And("o local do procedimento é {string}")
    public void o_local_do_procedimento_e(String localEsperado) {
        assertFalse(resultados.isEmpty(), "A lista não deve estar vazia");
        assertEquals(localEsperado, resultados.get(0).getLocal(), "Local incorreto");
    }

    @And("o tipo do procedimento é {string}")
    public void o_tipo_do_procedimento_e(String tipoStr) {
        TipoProcedimento tipoEsperado = TipoProcedimento.valueOf(tipoStr);
        assertFalse(resultados.isEmpty(), "A lista não deve estar vazia");
        assertEquals(tipoEsperado, resultados.get(0).getTipo(), "Tipo de procedimento incorreto");
    }
}
