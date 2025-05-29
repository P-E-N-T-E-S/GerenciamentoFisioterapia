package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaImplanta;
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
    public void salvar(FichaMedicaImplanta fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaRepositorio.save(fichaMedicaJPA);
    }

    @Override
    public void deletar(FichaMedicaImplanta fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaRepositorio.delete(fichaMedicaJPA);
    }

    @Override
    public void editar(FichaMedicaImplanta fichaMedica, long id) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        FichaMedicaJPA atual = fichaMedicaRepositorio.findById(id);

        if (atual != null){
            atual.setAlergias(fichaMedicaJPA.getAlergias());
            atual.setHistoricoMedico(fichaMedica.getHistoricoMedico());
            atual.setObservacoes(fichaMedica.getObservacoes());
            atual.setPaciente(fichaMedicaJPA.getPaciente());
            fichaMedicaRepositorio.save(atual);
        }else{
            fichaMedicaRepositorio.save(fichaMedicaJPA);
        }
    }

    @Override
    public List<FichaMedicaImplanta> listarFichaMedica() {
        List<FichaMedicaJPA> fichas = fichaMedicaRepositorio.findAll();
        return fichas.stream()
                .map(f -> mapeador.map(f, FichaMedicaImplanta.class))
                .toList();
    }

    @Override
    public FichaMedicaImplanta buscarPorId(long id) {
        FichaMedicaJPA fichaMedicaJPA = fichaMedicaRepositorio.findById(id);
        return mapeador.map(fichaMedicaJPA, FichaMedicaImplanta.class);
    }

    @Override
    public FichaMedicaImplanta buscarPorPaciente(PacienteId idPaciente) {
        Optional<FichaMedicaJPA> fichaMedicaJPA = fichaMedicaRepositorio.findByPacienteId(idPaciente.getId());
        return fichaMedicaJPA.map(f -> mapeador.map(f, FichaMedicaImplanta.class)).orElse(null);
    }
}
