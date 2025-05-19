package com.implantodontia.apresentacao.controllers;

import com.implantodontia.infraestrutura.security.auth.AuthDTO;
import com.implantodontia.infraestrutura.security.auth.AuthService;
import com.implantodontia.infraestrutura.security.auth.RegistroUsuarioDTO;
import com.implantodontia.infraestrutura.security.exceptions.UsuarioJaExistente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody AuthDTO authDto) {
        try {
            return ResponseEntity.ok(authService.login(authDto));
        }catch (BadCredentialsException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            }
        }

    @PostMapping("/register")
    public ResponseEntity<Object> cadastrar(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        try {
            return ResponseEntity.ok(authService.registrar(registroUsuarioDTO));
        }catch (UsuarioJaExistente e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario com login: " + e.getMessage()+ " ja existente");
        }
    }
}