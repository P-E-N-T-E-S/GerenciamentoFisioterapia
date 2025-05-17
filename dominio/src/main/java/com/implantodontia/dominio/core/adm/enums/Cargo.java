package com.implantodontia.dominio.core.adm.enums;

public enum Cargo {
    ASSISTENTE(0, "Assistente"),
    ADMINISTRADOR(1, "Administrador");;

    private final int codigo;
    private final String descricao;

    Cargo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Cargo fromIdentificador(long id) {
        for (Cargo cargo : Cargo.values()) {
            if (cargo.getCodigo() == id) {
                return cargo;
            }
        }
        throw new IllegalArgumentException("Cargo não encontrado para o id: " + id);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
