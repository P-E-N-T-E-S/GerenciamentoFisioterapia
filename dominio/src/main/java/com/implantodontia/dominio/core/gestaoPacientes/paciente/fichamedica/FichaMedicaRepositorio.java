package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import java.util.Optional;

public interface FichaMedicaRepositorio {
    public void salvar(FichaMedica fichaMedica);
    public Optional<FichaMedica> listarFichaMedica();
    public  FichaMedica buscarPorId(long id);
    public FichaMedica buscarPorPaciente();
}
