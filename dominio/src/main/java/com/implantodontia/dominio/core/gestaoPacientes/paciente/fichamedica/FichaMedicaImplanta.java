package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

public class FichaMedicaImplanta extends FichaMedica {
    public FichaMedicaImplanta(PacienteId pacienteId, long id) {
        super(pacienteId, id);
    }

    @Override
    protected boolean validarDadosEspecificos() {
        return true;
    }

    @Override
    protected void processarObservacao(String observacao) {

    }
}
