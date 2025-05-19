package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import java.time.LocalDate;

public class Procedimento {
    private final LocalDate dataRealizacao;
    private final TipoProcedimento tipo;
    private final String detalhes;
    private final String local;

    public Procedimento(LocalDate dataRealizacao, TipoProcedimento tipo,
                        String detalhes, String local) {
        this.dataRealizacao = dataRealizacao;
        this.tipo = tipo;
        this.detalhes = detalhes;
        this.local = local;
    }

    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public TipoProcedimento getTipo() { return tipo; }
    public String getDetalhes() { return detalhes; }
    public String getLocal() { return local; }
}