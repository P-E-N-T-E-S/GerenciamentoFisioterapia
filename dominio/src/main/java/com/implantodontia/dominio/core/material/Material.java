package com.implantodontia.dominio.core.material;


public class Material {
    private Long id;
    private int quantidade;
    private String nome;

    public Material(int quantidade, String nome) {
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public Material(Long id, int quantidade, String nome) {
        this.id = id;
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

    public Long getId() {
        return id;
    }
}
