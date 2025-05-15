package com.implantodontia.dominio.support.relatorio;

import java.util.List;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

public interface EstrategiaFiltroProcedimento {
    List<Procedimento> filtrar(List<Procedimento> procedimentos);
}
