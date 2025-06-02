package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.exceptions.ConsultaNaoEncontradaException;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialServico;

import java.time.LocalDate;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository repository;
    private final MaterialServico materialServico;

    public ConsultaService(ConsultaRepository repository, MaterialServico materialServico) {
        this.repository = repository;
        this.materialServico = materialServico;
    }

    public void salvar(Consulta consulta) {
        repository.salvar(consulta);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        return repository.buscarConsultaPorData(data);
    }

    public List<Consulta> listarConsultas(){
        return repository.listarConsultas();
    }

    public Consulta buscarPorId(Long id){
        return repository.buscarPorId(id);
    }

    public void deletarPorId(Long id){
        Consulta consulta = buscarPorId(id);
        if(consulta != null) {
            repository.deletar(consulta);
        }
    }

    public void editar(Consulta consulta){
        Consulta consultaAntiga = buscarPorId(consulta.getConsultaId().getId());
        List<Material> materiaisAtualizados = new java.util.ArrayList<>(List.of());
        if (consultaAntiga == null){
            throw new ConsultaNaoEncontradaException("Consulta de id: "+consulta.getConsultaId().getId()+" não encontrada");
        }
        if (consultaAntiga.getMateriais() != null) {
            for(Material material : consulta.getMateriais()) {
                processarMateriaisNovos(material);
                materiaisAtualizados.add(materialServico.buscarPorNome(material.getNome()));
            }
        }else {
            for(Material material : consulta.getMateriais()) {
                processarMateriaisAntigos(material, consultaAntiga.getMateriais().get(consultaAntiga.getMateriais().indexOf(material)));
                materiaisAtualizados.add(materialServico.buscarPorNome(material.getNome()));
            }
        }
        consulta.setMateriais(materiaisAtualizados);
        salvar(consulta);

    }

    private void processarMateriaisAntigos(Material novo, Material antigo){
        if(novo.getQuantidade() > antigo.getQuantidade()){
            materialServico.remover(novo.getNome(),novo.getQuantidade() - antigo.getQuantidade());
        } else if (antigo.getQuantidade() > novo.getQuantidade()) {
            materialServico.adicionar(novo.getNome(),antigo.getQuantidade() - novo.getQuantidade());
        }else{
            return;
        }
    }

    private void processarMateriaisNovos(Material novo){
        materialServico.remover(novo.getNome(),novo.getQuantidade());
        }

    public List<Consulta> buscarPorDataVencimento(LocalDate data){
        return repository.buscarConsultaPorDataVencimento(data);
    }
}
