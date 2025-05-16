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
}
