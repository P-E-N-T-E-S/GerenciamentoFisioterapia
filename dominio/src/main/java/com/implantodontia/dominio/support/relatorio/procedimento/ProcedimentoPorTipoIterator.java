package com.implantodontia.dominio.support.relatorio.procedimento;

import java.util.List;
import java.util.NoSuchElementException;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.TipoProcedimento;

// Iterador para procedimentos por tipo
public class ProcedimentoPorTipoIterator implements ProcedimentoIterator {
    private final List<Procedimento> procedimentos;
    private final TipoProcedimento tipoFiltro;
    private int posicao = 0;

    public ProcedimentoPorTipoIterator(List<Procedimento> procedimentos, TipoProcedimento tipoFiltro) {
        this.procedimentos = procedimentos;
        this.tipoFiltro = tipoFiltro;
    }

    @Override
    public boolean hasNext() {
        while (posicao < procedimentos.size()) {
            if (procedimentos.get(posicao).getTipo() == tipoFiltro) {
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
        throw new NoSuchElementException("Não há mais procedimentos deste tipo");
    }

    @Override
    public void reset() {
        posicao = 0;
    }
}