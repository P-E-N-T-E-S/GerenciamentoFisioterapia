package com.implantodontia.infraestrutura.security.auth;

public record RegistroUsuarioDTO(String login, String nome, String senha, String email, int cargo) {
}
