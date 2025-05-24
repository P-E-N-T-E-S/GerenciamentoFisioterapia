package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

import java.time.LocalDateTime;

public class FichaMedica {
    private final PacienteId pacienteId;
    private String historicoMedico;
    private String alergias;
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;

    public FichaMedica(PacienteId pacienteId, String historicoMedico, String alergias, String observacoes, LocalDateTime ultimaAtualizacao) {
        this.pacienteId = pacienteId;
        this.historicoMedico = historicoMedico;
        this.alergias = alergias;
        this.observacoes = observacoes;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public FichaMedica(PacienteId pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setHistoricoMedico(String historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public PacienteId getPacienteId() {
        return pacienteId;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getHistoricoMedico() {
        return historicoMedico;
    }

    public String getAlergias() {
        return alergias;
    }

}
