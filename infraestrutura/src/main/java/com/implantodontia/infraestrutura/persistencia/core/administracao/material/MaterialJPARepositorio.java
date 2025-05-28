package com.implantodontia.infraestrutura.persistencia.core.administracao.material;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialJPARepositorio extends JpaRepository<MaterialJPA, Long> {
    Optional<MaterialJPA> findByNome(String nome);
    MaterialJPA findById(long id);
}
