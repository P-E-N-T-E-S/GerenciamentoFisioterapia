package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;

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
