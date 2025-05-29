package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteRepository;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteImpl implements PacienteRepository {

    private PacienteJPARepositorio repositorio;
    private JpaMapeador mapper;

    public PacienteImpl(PacienteJPARepositorio repositorio, JpaMapeador mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public void cadastrar(Paciente paciente) {
        repositorio.save(mapper.map(paciente, PacienteJPA.class));
    }

    @Override
    public Paciente buscarPorId(PacienteId id) {
        return mapper.map(repositorio.findById(id.getId()).orElse(null), Paciente.class);
    }

    @Override
    public void deletar(PacienteId id) {
        repositorio.findById(id.getId()).ifPresent(paciente -> repositorio.delete(paciente));
    }

    @Override
    public List<Paciente> listarPacientes() {
        return repositorio.findAll().stream().map(jpa -> mapper.map(jpa, Paciente.class)).toList();
    }
}
