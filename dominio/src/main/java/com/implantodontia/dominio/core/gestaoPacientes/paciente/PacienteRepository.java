package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository {
    void cadastrar(Paciente paciente);
    Paciente buscarPorId(PacienteId id);
    void deletar(PacienteId id);
}
