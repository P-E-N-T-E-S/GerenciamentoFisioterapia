package com.implantodontia.infraestrutura.persistencia.core.material;

import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialRepository;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialImpl implements MaterialRepository {

    MaterialJPARepositorio materialJPARepositorio;
    JpaMapeador mapeador;

    public MaterialImpl(MaterialJPARepositorio materialJPARepositorio, JpaMapeador mapeador) {
        this.materialJPARepositorio = materialJPARepositorio;
        this.mapeador = mapeador;
    }

    @Override
    public void salvar(Material material) {
        MaterialJPA materialJPA = mapeador.map(material, MaterialJPA.class);
        materialJPARepositorio.save(materialJPA);
    }

    @Override
    public void deletar(Long id) {
        materialJPARepositorio.findById(id).ifPresent(materialJPA -> materialJPARepositorio.delete(materialJPA));
    }

    @Override
    public void editar(Material material, long id) {
        MaterialJPA materialJPA = mapeador.map(material, MaterialJPA.class);
        MaterialJPA atual = materialJPARepositorio.findById(id);

        if(atual != null){
            atual.setNome(material.getNome());
            atual.setQuantidade(material.getQuantidade());
            materialJPARepositorio.save(atual);
        }else{
            materialJPARepositorio.save(materialJPA);
        }
    }

    @Override
    public void editarPorNome(Material material, String nome) {
        MaterialJPA materialJPA = materialJPARepositorio.findByNome(nome).orElse(null);
        if(materialJPA != null) {
            materialJPA.setNome(material.getNome());
            materialJPA.setQuantidade(material.getQuantidade());
            materialJPARepositorio.save(materialJPA);
        }
    }


    @Override
    public Material buscarPorNome(String nome) {
        MaterialJPA materialJPA = materialJPARepositorio.findByNome(nome).orElse(null);
        return materialJPA != null ? mapeador.map(materialJPA, Material.class) : null;
    }

    @Override
    public List<Material> buscarTodos() {
        List<MaterialJPA> materiaisJPA = materialJPARepositorio.findAll();
        return materiaisJPA.stream()
                .map(m -> mapeador.map(m, Material.class))
                .toList();
    }
}
