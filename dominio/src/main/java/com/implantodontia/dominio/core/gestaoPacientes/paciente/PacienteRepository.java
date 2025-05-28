package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository {
    void cadastrar(Paciente paciente);
    Paciente buscarPorId(PacienteId id);
    void deletar(PacienteId id);
    List<Paciente> listarPacientes();
}
