package com.implantodontia.dominio.core.adm;

import com.implantodontia.dominio.core.adm.enums.Cargo;

public class Usuario {
    private final String login;
    private final String email;
    private final String senha;
    private final Cargo cargo;

    public Usuario(String login, String email, String senha, Cargo cargo) {
        this.login = login;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }


    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Cargo getCargo() {
        return cargo;
    }
}
