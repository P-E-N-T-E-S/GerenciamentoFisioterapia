package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteRepository;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPARepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteImpl implements PacienteRepository {

    private PacienteJPARepositorio repositorio;
    private EnderecoJPARepositorio enderecoRepositorio;
    private JpaMapeador mapper;

    public PacienteImpl(PacienteJPARepositorio repositorio, EnderecoJPARepositorio enderecoRepositorio, JpaMapeador mapper) {
        this.repositorio = repositorio;
        this.enderecoRepositorio = enderecoRepositorio;
        this.mapper = mapper;
    }

    @Override
    public void cadastrar(Paciente paciente) {
        PacienteJPA pacienteJPA = mapper.map(paciente, PacienteJPA.class);
        if(enderecoRepositorio.findById(pacienteJPA.getEndereco().getNome()).isEmpty()) {
            enderecoRepositorio.save(pacienteJPA.getEndereco());
        }
        repositorio.save(pacienteJPA);
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
