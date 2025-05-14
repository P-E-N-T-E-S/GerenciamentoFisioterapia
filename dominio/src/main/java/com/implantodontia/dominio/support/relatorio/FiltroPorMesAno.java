package com.implantodontia.dominio.support.relatorio;

import java.util.List;
import java.util.stream.Collectors;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

public class FiltroPorMesAno implements EstrategiaFiltroProcedimento {
    private final int mes;
    private final int ano;

    public FiltroPorMesAno(int mes, int ano) {
        this.mes = mes;
        this.ano = ano;
    }

    @Override
    public List<Procedimento> filtrar(List<Procedimento> procedimentos) {
        return procedimentos.stream()
                .filter(p ->
                        p.getDataRealizacao().getMonthValue() == mes
                        && p.getDataRealizacao().getYear() == ano
                )
                .collect(Collectors.toList());
    }
}