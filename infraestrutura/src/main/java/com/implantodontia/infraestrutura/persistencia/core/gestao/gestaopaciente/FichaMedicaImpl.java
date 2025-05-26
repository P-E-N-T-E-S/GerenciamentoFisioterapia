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
    private final FichaMedicaJPARepositorio fichaMedicaJPARepositorio;

    public FichaMedicaImpl(FichaMedicaJPARepositorio fichaMedicaRepositorio, JpaMapeador mapeador, FichaMedicaJPARepositorio fichaMedicaJPARepositorio) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
        this.mapeador = mapeador;
        this.fichaMedicaJPARepositorio = fichaMedicaJPARepositorio;
    }

    @Override
    public void salvar(FichaMedica fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaRepositorio.save(fichaMedicaJPA);
    }

    @Override
    public void deletar(FichaMedica fichaMedica) {
        FichaMedicaJPA fichaMedicaJPA = mapeador.map(fichaMedica, FichaMedicaJPA.class);
        fichaMedicaJPARepositorio.delete(fichaMedicaJPA);
    }

    @Override
    public void editar(FichaMedica fichaMedica, long id) {
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
    public List<FichaMedica> listarFichaMedica() {
        List<FichaMedicaJPA> fichas = fichaMedicaRepositorio.findAll();
        return fichas.stream()
                .map(f -> mapeador.map(f, FichaMedica.class))
                .toList();
    }

    @Override
    public FichaMedica buscarPorId(long id) {
        FichaMedicaJPA fichaMedicaJPA = fichaMedicaRepositorio.findById(id);
        return mapeador.map(fichaMedicaJPA, FichaMedica.class);
    }

    @Override
    public FichaMedica buscarPorPaciente(long idPaciente) {
        Optional<FichaMedicaJPA> fichaMedicaJPA = fichaMedicaRepositorio.findByPacienteId(idPaciente);
        return fichaMedicaJPA.map(f -> mapeador.map(f, FichaMedica.class)).orElse(null);
    }
}
