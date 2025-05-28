package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void salvar(Consulta consulta) {
        repository.salvar(consulta);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        return repository.buscarPorData(data);
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
}
