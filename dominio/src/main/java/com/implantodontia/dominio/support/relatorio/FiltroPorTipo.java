package com.implantodontia.dominio.support.relatorio;

import java.util.List;
import java.util.stream.Collectors;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.TipoProcedimento;

public class FiltroPorTipo implements EstrategiaFiltroProcedimento {
    private final TipoProcedimento tipo;

    public FiltroPorTipo(TipoProcedimento tipo) {
        this.tipo = tipo;
    }

    @Override
    public List<Procedimento> filtrar(List<Procedimento> procedimentos) {
        return procedimentos.stream()
                .filter(p -> p.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}