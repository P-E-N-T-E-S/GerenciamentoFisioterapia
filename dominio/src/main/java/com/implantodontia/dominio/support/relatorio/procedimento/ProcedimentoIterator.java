package com.implantodontia.dominio.support.relatorio.procedimento;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;

// Interface Iterator
public interface ProcedimentoIterator {
    boolean hasNext();
    Procedimento next();
    void reset(); // Opcional: para reiniciar a iteração
}




