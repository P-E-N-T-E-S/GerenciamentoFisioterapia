package com.implantodontia.infraestrutura.persistencia.core.administracao.usuario;

import com.implantodontia.dominio.core.adm.enums.Cargo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UsuarioJPA {
    @Id
    private String login;
    private String email;
    private String senha;
    private int cargo;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }
}
