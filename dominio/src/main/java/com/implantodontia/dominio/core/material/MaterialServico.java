package com.implantodontia.dominio.core.material;

import com.implantodontia.dominio.core.material.exceptions.MaterialNaoEncontradoException;
import com.implantodontia.dominio.core.material.exceptions.QuantidadeIndisponivelException;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaterialServico {

    private MaterialRepository materialRepository;
    private NotificacaoService notificacaoService;

    public MaterialServico(MaterialRepository materialRepository, NotificacaoService notificacaoService) {
        this.materialRepository = materialRepository;
        this.notificacaoService = notificacaoService;
    }

    public void adicionar(String nome, int quantidade) {
        Material material = materialRepository.buscarPorNome(nome);
        if(material != null) {
            material.adicionarMaterial(quantidade);
            materialRepository.editarPorNome(material, material.getNome());
        }else{
            material = new Material(quantidade, nome);
            materialRepository.salvar(material);
        }
    }

    public void remover(String nome, int quantidade) {
        Material material = materialRepository.buscarPorNome(nome);
        if (material == null) {
            throw new MaterialNaoEncontradoException("Material: " + nome + " não encontrado");
        } else {
            if(quantidade > material.getQuantidade()){
                throw new QuantidadeIndisponivelException("Há apenas "+material.getQuantidade()+" de "+nome);
            }
            material.usarMaterial(quantidade);
            materialRepository.editarPorNome(material, material.getNome());
            if (material.getQuantidade() < 3) {
                String notificacao =  ("preciso adquirir mais " + material.getNome());
                notificacaoService.notificarUsuario("fisioterapeuta@gmail.com", notificacao, TipoNotificacao.MATERIAIS);
            }
        }
    }

    public List<Material> listarMateriais() {
        return materialRepository.buscarTodos();
    }

    public void editar(String nome, int quantidade, Long id) {
        Material material = new  Material(quantidade, nome);
        materialRepository.editar(material, id);
    }

    public void deletarPorId(Long id) {
        materialRepository.deletar(id);
    }
}
