package com.implantodontia.dominio.core.material;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository {
    void salvar(Material material);
    void deletar(Material material);
    void editar(Material material, long id);

    Material buscarPorNome(String nome);
    List<Material> buscarTodos();
}
