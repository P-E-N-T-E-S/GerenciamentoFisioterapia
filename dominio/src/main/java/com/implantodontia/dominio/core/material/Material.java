package com.implantodontia.dominio.core.material;


public class Material {
    private int quantidade;
    private String nome;

    public Material(int quantidade, String nome) {
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public void adicionarMaterial(int quantidade){
        this.quantidade += quantidade;
    }

    public void usarMaterial(int quantidade){
        this.quantidade -= quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getNome() {
        return nome;
    }
}
