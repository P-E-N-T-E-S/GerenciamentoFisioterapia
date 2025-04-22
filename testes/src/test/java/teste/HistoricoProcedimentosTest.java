package teste;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.support.relatorio.HistoricoProcedimentosService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HistoricoProcedimentosTest {

    private HistoricoProcedimentosService service;

    @BeforeEach
    void setUp() {
        service = HistoricoProcedimentosService.getInstance();
        service.limparProcedimentos();
    }

    @Test
    void deveRetornarNenhumProcedimentoParaDataEspecifica() {
        LocalDate dataConsulta = LocalDate.of(2020, 8, 29);
        List<Procedimento> resultado = service.filtrarPorDataExata(dataConsulta);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveFiltrarProcedimentosPorTipo() {
        service.adicionarProcedimento(new Procedimento(
                LocalDate.now(),
                Procedimento.TipoProcedimento.INTRA_OPERATORIO,
                "Procedimento intraoperatório de reabilitação",
                "Hospital Geral"
        ));

        service.adicionarProcedimento(new Procedimento(
                LocalDate.now(),
                Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                "Consulta Domiciliar",
                "Rua dos Principes"
        ));

        List<Procedimento> resultado = service.filtrarPorTipo(
                Procedimento.TipoProcedimento.INTRA_OPERATORIO
        );

        assertEquals(1, resultado.size());
        assertEquals("Hospital Geral", resultado.get(0).getLocal());
    }

    @Test
    void deveFiltrarPorMesEAno() {
        service.adicionarProcedimento(new Procedimento(
                LocalDate.of(2024, 3, 15),
                Procedimento.TipoProcedimento.CONSULTA_PADRAO,
                "Recuperação pós-artroscopia no consultório",
                "Consultório"
        ));

        List<Procedimento> resultado = service.filtrarPorMesAno(3, 2024);
        assertEquals(1, resultado.size());
        assertEquals(Procedimento.TipoProcedimento.CONSULTA_PADRAO, resultado.get(0).getTipo());
    }
}