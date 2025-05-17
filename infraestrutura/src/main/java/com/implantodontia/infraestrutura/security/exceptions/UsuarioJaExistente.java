package com.implantodontia.infraestrutura.security.exceptions;

public class UsuarioJaExistente extends RuntimeException {
    public UsuarioJaExistente(String message) {
        super(message);
    }
}
