package com.implantodontia.dominio.support.relatorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

public class HistoricoProcedimentosService {
    private static HistoricoProcedimentosService instance;
    private final List<Procedimento> procedimentos = new ArrayList<>();

    private HistoricoProcedimentosService() {}

    public static synchronized HistoricoProcedimentosService getInstance() {
        if (instance == null) {
            instance = new HistoricoProcedimentosService();
        }
        return instance;
    }

    public void adicionarProcedimento(Procedimento procedimento) {
        procedimentos.add(procedimento);
    }

    public List<Procedimento> filtrarPorDataExata(LocalDate data) {
        return procedimentos.stream()
                .filter(p -> p.getDataRealizacao().equals(data))
                .collect(Collectors.toList());
    }

    public List<Procedimento> filtrarPorMesAno(int mes, int ano) {
        return procedimentos.stream()
                .filter(p ->
                        p.getDataRealizacao().getMonthValue() == mes
                                && p.getDataRealizacao().getYear() == ano
                )
                .collect(Collectors.toList());
    }

    public List<Procedimento> filtrarPorTipo(Procedimento.TipoProcedimento tipo) {
        return procedimentos.stream()
                .filter(p -> p.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    public void limparProcedimentos() {
        procedimentos.clear();
    }
}