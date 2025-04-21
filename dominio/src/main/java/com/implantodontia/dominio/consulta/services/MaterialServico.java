package com.implantodontia.dominio.consulta.services;

import java.util.Map;

public class MaterialServico {

    private static MaterialServico instance;

    public void adicionar(Map<String, Integer> inventario, String item, int quantidade) {
        if (inventario.containsKey(item)) {
            inventario.computeIfPresent(item, (key, value) -> value + quantidade);
        } else {
            inventario.put(item, quantidade);
        }
    }

    public String remover(Map<String, Integer> inventario, String item, int quantidade) {
        if (inventario.containsKey(item)) {
            inventario.computeIfPresent(item, (key, value) -> value - quantidade);
        } else {
            return "";
        }
        if (inventario.get(item) < 3) {
            return("preciso adquirir mais " + item);
        }
        return "";
    }

    public static synchronized MaterialServico getInstance() {
        if (instance == null) {
            instance = new MaterialServico();
        }
        return instance;
    }
}
