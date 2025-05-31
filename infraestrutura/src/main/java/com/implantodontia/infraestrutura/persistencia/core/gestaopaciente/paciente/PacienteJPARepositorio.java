package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteJPARepositorio extends JpaRepository<PacienteJPA, Long> {
    List<PacienteJPA> findByNomeContaining(String nome);

}
