package com.implantodontia.infraestrutura.persistencia.core.gestao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichaMedicaJPARepositorio extends JpaRepository<FichaMedicaJPA, Long> {
    Optional<FichaMedicaJPA> findByPacienteId(Long id);
    Optional<FichaMedicaJPA> findById(Long id);

}
