package com.implantodontia.dominio.core.gestaoPacientes.paciente;

public interface PacienteRepository {
    void cadastrar(Paciente paciente);
    Paciente buscarPorId(PacienteId id);
    void deletar(PacienteId id);
}
