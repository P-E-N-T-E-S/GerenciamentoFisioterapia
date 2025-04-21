package com.implantodontia.steps;

import io.cucumber.java.pt.*;
import com.implantodontia.dominio.consulta.Procedimento;
import com.implantodontia.dominio.consulta.services.HistoricoProcedimentosService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HistoricoProcedimentosSteps {

    private final HistoricoProcedimentosService service = HistoricoProcedimentosService.getInstance();
    private List<Procedimento> resultados;
    private LocalDate dataFiltro;
    private int mesFiltro;
    private int anoFiltro;

    // ==================================================================
    // Cenário: Nenhum procedimento em data específica
    // ==================================================================
    @Dado("não existem procedimentos cadastrados")
    public void limparProcedimentos() {
        service.limparProcedimentos();
    }

    @Quando("eu busco procedimentos na data {string}")
    public void buscarPorData(String data) {
        this.dataFiltro = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        resultados = service.filtrarPorDataExata(dataFiltro);
    }

    @Então("o sistema retorna uma lista vazia")
    public void verificarListaVazia() {
        assertTrue(resultados.isEmpty());
    }

    // ==================================================================
    // Cenário: Filtrar procedimentos por tipo
    // ==================================================================
    @Dado("existem procedimentos cadastrados")
    public void cadastrarProcedimentos() {
        service.limparProcedimentos();
        service.adicionarProcedimento(
                new Procedimento(
                        LocalDate.now(),
                        Procedimento.TipoProcedimento.INTRA_OPERATORIO,
                        "Procedimento intraoperatório de reabilitação",
                        "Hospital Geral"
                )
        );
        service.adicionarProcedimento(
                new Procedimento(
                        LocalDate.now(),
                        Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                        "Consulta Domiciliar",
                        "Rua dos Principes"
                )
        );
        service.adicionarProcedimento(
                new Procedimento(
                LocalDate.of(2024, 3, 15), // 15 de março de 2024 (mês 3)
                Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                "Recuperação pós-artroscopia no consultório",
                "Consultório"
        ));
    }

    @Quando("eu filtro por tipo {string}")
    public void filtrarPorTipo(String tipo) {
        resultados = service.filtrarPorTipo(Procedimento.TipoProcedimento.valueOf(tipo));
    }

    @Então("o sistema retorna {int} procedimento")
    public void verificarQuantidadeResultados(int quantidade) {
        assertEquals(quantidade, resultados.size());
    }

    @Então("o local do procedimento é {string}")
    public void verificarLocal(String local) {
        assertEquals(local, resultados.get(0).getLocal());
    }

    // ==================================================================
    // Cenário: Filtrar procedimentos por mês e ano
    // ==================================================================
    @Quando("eu filtro pelo mês {int} e ano {int}")
    public void filtrarPorMesAno(Integer mes, Integer ano) {
        this.mesFiltro = mes;
        this.anoFiltro = ano;
        resultados = service.filtrarPorMesAno(mes, ano);
    }

    @Então("o tipo do procedimento é {string}")
    public void verificarTipo(String tipo) {
        assertEquals(Procedimento.TipoProcedimento.valueOf(tipo), resultados.get(0).getTipo());
    }
}