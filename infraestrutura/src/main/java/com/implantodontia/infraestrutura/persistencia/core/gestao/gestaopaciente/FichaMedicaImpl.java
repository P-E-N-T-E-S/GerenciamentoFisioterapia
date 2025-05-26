package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaRepositorio;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FichaMedicaImpl implements FichaMedicaRepositorio {

    private final FichaMedicaJPARepositorio fichaMedicaRepositorio;
    private final JpaMapeador mapeador;

    public FichaMedicaImpl(FichaMedicaJPARepositorio fichaMedicaRepositorio, JpaMapeador mapeador) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
        this.mapeador = mapeador;
    }

    @Override
    public void salvar(FichaMedica fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaRepositorio.save(fichaMedicaJPA);
    }

    @Override
    public List<FichaMedica> listarFichaMedica() {
        List<FichaMedicaJPA> fichas = fichaMedicaRepositorio.findAll();
        return fichas.stream()
                .map(f -> mapeador.map(f, FichaMedica.class))
                .toList();
    }

    @Override
    public FichaMedica buscarPorId(long id) {
        FichaMedicaJPA fichaMedicaJPA = fichaMedicaRepositorio.findById(id).orElse(null);
        return mapeador.map(fichaMedicaJPA, FichaMedica.class);
    }

    @Override
    public FichaMedica buscarPorPaciente(long idPaciente) {
        Optional<FichaMedicaJPA> fichaMedicaJPA = fichaMedicaRepositorio.findByPacienteId(idPaciente);
        return fichaMedicaJPA.map(f -> mapeador.map(f, FichaMedica.class)).orElse(null);
    }
}
