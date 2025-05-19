package com.implantodontia.dominio.support.relatorio;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

public class FiltroPorDataExata implements EstrategiaFiltroProcedimento {
    private final LocalDate data;

    public FiltroPorDataExata(LocalDate data) {
        this.data = data;
    }

    @Override
    public List<Procedimento> filtrar(List<Procedimento> procedimentos) {
        return procedimentos.stream()
                .filter(p -> p.getDataRealizacao().equals(data))
                .collect(Collectors.toList());
    }
}