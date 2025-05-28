package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

public class FichaMedicaImplanta extends FichaMedica {
    public FichaMedicaImplanta(PacienteId pacienteId) {
        super(pacienteId);
    }

    @Override
    protected boolean validarDadosEspecificos() {
        return false;
    }

    @Override
    protected void processarObservacao(String observacao) {

    }
}
