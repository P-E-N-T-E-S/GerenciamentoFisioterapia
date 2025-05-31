package com.implantodontia.dominio.core.material;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository {
    void salvar(Material material);
    void deletar(Long id);
    void editar(Material material, long id);
    void editarPorNome(Material material, String nome);

    Material buscarPorNome(String nome);
    List<Material> buscarTodos();
}
