package com.implantodontia.dominio.support.relatorio.procedimento;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

// Iterador para procedimentos por data
public class ProcedimentoPorDataIterator implements ProcedimentoIterator {
    private final List<Procedimento> procedimentos;
    private final LocalDate dataFiltro;
    private int posicao = 0;

    public ProcedimentoPorDataIterator(List<Procedimento> procedimentos, LocalDate dataFiltro) {
        this.procedimentos = procedimentos;
        this.dataFiltro = dataFiltro;
    }

    @Override
    public boolean hasNext() {
        while (posicao < procedimentos.size()) {
            if (procedimentos.get(posicao).getDataRealizacao().equals(dataFiltro)) {
                return true;
            }
            posicao++;
        }
        return false;
    }

    @Override
    public Procedimento next() {
        if (hasNext()) {
            return procedimentos.get(posicao++);
        }
        throw new NoSuchElementException("Não há mais procedimentos para esta data");
    }

    @Override
    public void reset() {
        posicao = 0;
    }
}
