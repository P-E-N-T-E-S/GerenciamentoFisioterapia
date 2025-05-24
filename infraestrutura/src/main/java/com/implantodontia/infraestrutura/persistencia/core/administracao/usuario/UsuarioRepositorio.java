package com.implantodontia.infraestrutura.persistencia.core.administracao.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

public interface UsuarioRepositorio extends JpaRepository<UsuarioJPA, String> {
}
