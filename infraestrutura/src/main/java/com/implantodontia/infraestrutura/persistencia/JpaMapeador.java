package com.implantodontia.infraestrutura.persistencia;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

public class JpaMapeador extends ModelMapper {

    JpaMapeador() {
        var configuracao = getConfiguration();
        configuracao.setFieldMatchingEnabled(true);
        configuracao.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        addConverter(new AbstractConverter<Usuario, UsuarioJPA>() {
            @Override
            protected UsuarioJPA convert(Usuario source) {
                UsuarioJPA usuarioJPA = new UsuarioJPA();
                usuarioJPA.setLogin(source.getLogin());
                usuarioJPA.setSenha((source.getSenha()));
                usuarioJPA.setCargo(source.getCargo().getCodigo());
                usuarioJPA.setEmail(source.getEmail());
                return usuarioJPA;
            }
        });

        addConverter(new AbstractConverter<UsuarioJPA, Usuario>() {

            @Override
            protected Usuario convert(UsuarioJPA source) {
                return new Usuario(source.getLogin(), source.getEmail(), source.getSenha(), Cargo.fromIdentificador(source.getCargo()));
            }
        });

    }
}
