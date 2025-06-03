package com.implantodontia.infraestrutura.security.auth;

import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioRepositorio;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import com.implantodontia.infraestrutura.security.exceptions.UsuarioJaExistente;
import com.implantodontia.infraestrutura.security.jwt.JwtUtils;
import com.implantodontia.infraestrutura.security.userdetail.UsuarioDetalhes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private UsuarioRepositorio usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UsuarioRepositorio usuarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AcessDTO login(AuthDTO authDTO){

        try {
            UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDTO.username(), authDTO.password());

            Authentication authentication = authenticationManager.authenticate(userAuth);

            UsuarioDetalhes userDetails = (UsuarioDetalhes) authentication.getPrincipal();

            String token = jwtUtils.generateTokenFromUserDetails(userDetails);

            String cargo = userDetails.getAuthorities().iterator().next().getAuthority();

            AcessDTO acessDTO = new AcessDTO(token, cargo);

            return acessDTO;
        }catch (BadCredentialsException e){
            //TODO: Login ou senha invalidos
            System.out.println(e.getMessage());
        }
        return null;
    }

    public AcessDTO registrar(RegistroUsuarioDTO registroUsuarioDTO) throws UsuarioJaExistente {
        if(usuarioRepository.existsById(registroUsuarioDTO.login())){
            throw new UsuarioJaExistente(registroUsuarioDTO.login());
        }
        UsuarioJPA usuario = new UsuarioJPA();
        usuario.setLogin(registroUsuarioDTO.login());
        usuario.setSenha(passwordEncoder.encode(registroUsuarioDTO.senha()));
        usuario.setCargo(registroUsuarioDTO.cargo());
        usuario.setEmail(registroUsuarioDTO.email());
        usuarioRepository.save(usuario);

        return login(new AuthDTO(registroUsuarioDTO.login(), registroUsuarioDTO.senha()));
    }
}
