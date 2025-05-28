package com.implantodontia.infraestrutura.persistencia.core.administracao.material;

import jakarta.persistence.*;

@Entity
@Table (name = "material")
public class MaterialJPA {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    long id;

    int quantidade;
    String nome;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
