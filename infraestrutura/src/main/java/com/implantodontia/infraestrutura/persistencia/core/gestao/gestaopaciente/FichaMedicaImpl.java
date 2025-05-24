package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaRepositorio;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FichaMedicaImpl implements FichaMedicaRepositorio {

    private FichaMedicaJPARepositorio fichaMedicaRepositorio;
    private JpaMapeador mapeador;

    public FichaMedicaImpl(FichaMedicaJPARepositorio fichaMedicaJPARepositorio, JpaMapeador mapeador) {
        this.fichaMedicaRepositorio = fichaMedicaJPARepositorio;
        this.mapeador = mapeador;
    }

    @Override
    public void salvar(FichaMedica fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaRepositorio.save(fichaMedicaJPA);
    }

    @Override
    public Optional<FichaMedica> listarFichaMedica() {
        return Optional.empty();
    }

    @Override
    public FichaMedica buscarPorId(long id) {
        FichaMedicaJPA fichaMedicaJPA = fichaMedicaRepositorio.findById(id).orElse(null);
        return mapeador.map(fichaMedicaJPA, FichaMedica.class);
    }

    @Override
    public FichaMedica buscarPorPaciente() {
        return null;
    }


}
