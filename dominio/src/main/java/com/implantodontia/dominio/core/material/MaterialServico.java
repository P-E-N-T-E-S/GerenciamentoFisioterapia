package com.implantodontia.dominio.core.material;

import com.implantodontia.dominio.core.material.exceptions.MaterialNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaterialServico {

    private MaterialRepository materialRepository;

    public MaterialServico(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void adicionar(String nome, int quantidade) {
        Material material = materialRepository.buscarPorNome(nome);
        if(material != null) {
            material.adicionarMaterial(quantidade);
        }else{
            material = new Material(quantidade, nome);
        }
        materialRepository.salvar(material);
    }

    public void remover(String nome, int quantidade) {
        Material material = materialRepository.buscarPorNome(nome);
        if (material == null) {
            throw new MaterialNaoEncontradoException("Material: " + nome + " não encontrado");
        } else {
            material.usarMaterial(quantidade);
            if (material.getQuantidade() < 3) {
                String notificacao =  ("preciso adquirir mais " + material.getNome()); //TODO: colocar isso em uma mensageria, eu não gosto de desenvolver essa bomba
            }
        }
    }

    public List<Material> listarMateriais() {
        return materialRepository.buscarTodos();
    }
}
