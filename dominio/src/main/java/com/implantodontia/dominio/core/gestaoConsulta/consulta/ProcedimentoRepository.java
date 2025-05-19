package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import java.time.LocalDate;
import java.util.List;

public interface ProcedimentoRepository {
    void salvar(Procedimento procedimento);
    List<Procedimento> buscarTodos();
    List<Procedimento> buscarPorData(LocalDate data);
    List<Procedimento> buscarPorTipo(TipoProcedimento tipo);
    List<Procedimento> buscarPorDataETipo(LocalDate data, TipoProcedimento tipo);
}
