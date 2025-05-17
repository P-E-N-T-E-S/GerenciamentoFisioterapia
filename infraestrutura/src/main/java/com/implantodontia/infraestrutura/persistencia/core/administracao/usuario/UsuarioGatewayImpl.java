package com.implantodontia.infraestrutura.persistencia.core.administracao.usuario;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.UsuarioGateway;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioGatewayImpl implements UsuarioGateway {

    private UsuarioRepositorio usuarioRepositorio;

    private JpaMapeador mapeador;

    public UsuarioGatewayImpl(UsuarioRepositorio usuarioRepositorio, JpaMapeador mapeador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.mapeador = mapeador;
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        Usuario usuario = mapeador.map(usuarioRepositorio.findById(login).orElse(null), Usuario.class);
        return Optional.ofNullable(usuario);
    }
}
