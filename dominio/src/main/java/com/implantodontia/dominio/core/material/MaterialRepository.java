package com.implantodontia.dominio.core.material;

import java.util.List;

public interface MaterialRepository {
    void salvar(Material material);
    void deletar(Material material);
    Material buscarPorNome(String nome);
    List<Material> buscarTodos();
}
