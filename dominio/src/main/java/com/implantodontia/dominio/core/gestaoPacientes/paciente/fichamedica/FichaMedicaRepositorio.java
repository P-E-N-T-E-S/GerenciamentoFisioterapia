package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import java.util.List;
import java.util.Optional;

public interface FichaMedicaRepositorio {
    public void salvar(FichaMedica fichaMedica);
    public void deletar(FichaMedica fichaMedica);
    public void editar(FichaMedica fichaMedica, long id);
    public List<FichaMedica> listarFichaMedica();
    public  FichaMedica buscarPorId(long id);
    public FichaMedica buscarPorPaciente(long idPaciente);
}
