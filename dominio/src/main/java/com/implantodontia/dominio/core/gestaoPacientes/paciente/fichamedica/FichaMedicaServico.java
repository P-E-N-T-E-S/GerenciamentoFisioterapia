package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public class FichaMedicaServico {

    private FichaMedicaRepositorio fichaMedicaRepositorio;

    public FichaMedicaServico(FichaMedicaRepositorio fichaMedicaRepositorio) {
        this.fichaMedicaRepositorio = fichaMedicaRepositorio;
    }

    public void preencherDadosClinicos(PacienteId pacienteId, String historicoMedico, String alergias, LocalDateTime now) {
        FichaMedicaImplanta fichaMedica = fichaMedicaRepositorio.buscarPorPaciente(pacienteId);
        fichaMedica.setHistoricoMedico(historicoMedico);
        fichaMedica.setAlergias(alergias);
        fichaMedica.setUltimaAtualizacao(now);
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
        // :) Se esse metodo não funcionar a culpa é de @Pedro
    }
}
