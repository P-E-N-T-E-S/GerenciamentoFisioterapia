package com.implantodontia.dominio.core.adm;

import java.util.Optional;

public interface UsuarioGateway {
    Optional<Usuario> buscarPorLogin(String login);
}
