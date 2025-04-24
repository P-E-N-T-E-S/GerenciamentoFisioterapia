package com.implantodontia.dominio.core.material;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MaterialServico {

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
}
