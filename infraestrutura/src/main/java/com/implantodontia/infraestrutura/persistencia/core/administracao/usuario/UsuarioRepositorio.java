package com.implantodontia.infraestrutura.persistencia.core.administracao.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<UsuarioJPA, String> {
}
