package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FichaMedicaJPARepositorio extends JpaRepository<FichaMedicaJPA, Long> {
    Optional<FichaMedicaJPA> findByPacienteId(Long id);
    FichaMedicaJPA findById(long id);
}
