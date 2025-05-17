package com.implantodontia.infraestrutura.security.userdetail;

import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.UsuarioGateway;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioGateway usuarioGateway;

    public UsuarioDetailsService(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        Usuario usuario = usuarioGateway.buscarPorLogin(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com a matrícula: " + matricula));
        return new UsuarioDetalhes(usuario);
    }
}
