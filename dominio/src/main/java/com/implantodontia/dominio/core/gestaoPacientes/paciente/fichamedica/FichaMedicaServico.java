package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class FichaMedicaServico {

    private FichaMedicaRepositorio fichaMedicaRepositorio;

    public FichaMedicaServico(FichaMedicaRepositorio fichaMedicaRepositorio) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
    }

    public void preencherDadosClinicos(PacienteId pacienteId, String historicoMedico, String alergias) {
        FichaMedicaImplanta fichaMedica = fichaMedicaRepositorio.buscarPorPaciente(pacienteId);
        if (fichaMedica == null){
            criarFichaMedica(pacienteId);
            fichaMedica = fichaMedicaRepositorio.buscarPorPaciente(pacienteId);
        }
        fichaMedica.preencherDadosClinicos(historicoMedico, alergias);
        fichaMedicaRepositorio.salvar(fichaMedica);
    }

    public boolean validarDadosObrigatorios(PacienteId pacienteId){
        FichaMedicaImplanta fichaMedica = fichaMedicaRepositorio.buscarPorPaciente(pacienteId);
        return fichaMedica.getHistoricoMedico() != null && !fichaMedica.getHistoricoMedico().isBlank()
                && fichaMedica.getAlergias() != null && !fichaMedica.getAlergias().isBlank();
    }

    public void adicionarObservacao(PacienteId pacienteId, String observacao) {
        FichaMedicaImplanta fichaMedica = fichaMedicaRepositorio.buscarPorPaciente(pacienteId);
        fichaMedica.setObservacoes(observacao);
        fichaMedica.setUltimaAtualizacao(LocalDateTime.now());
        fichaMedicaRepositorio.salvar(fichaMedica);
    }

    public void criarFichaMedica(PacienteId pacienteId) {
        FichaMedicaImplanta fichaMedica = new FichaMedicaImplanta(pacienteId, 0);
        fichaMedicaRepositorio.salvar(fichaMedica);
    }
}
