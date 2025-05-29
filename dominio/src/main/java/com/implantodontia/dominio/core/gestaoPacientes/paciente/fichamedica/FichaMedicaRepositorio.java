package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

import java.util.List;
import java.util.Optional;

public interface FichaMedicaRepositorio {
    void salvar(FichaMedicaImplanta fichaMedica);
    void deletar(FichaMedicaImplanta fichaMedica);
    void editar(FichaMedicaImplanta fichaMedica, long id);
    List<FichaMedicaImplanta> listarFichaMedica();
    FichaMedicaImplanta buscarPorId(long id);
    FichaMedicaImplanta buscarPorPaciente(PacienteId idPaciente);
}
