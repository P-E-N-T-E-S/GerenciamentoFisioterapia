package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteJPARepositorio extends JpaRepository<PacienteJPA, Long> {
}
