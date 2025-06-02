package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

import java.time.LocalDateTime;

public abstract class FichaMedica {
    private final long id;
    private final PacienteId pacienteId;
    private String historicoMedico;
    private String alergias;
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;

    public FichaMedica(PacienteId pacienteId, long id) {
        this.pacienteId = pacienteId;
        this.id = id;
    }

    public final void preencherDadosClinicos(String historicoMedico, String alergias) {
        this.historicoMedico = historicoMedico;
        this.alergias = alergias;
        this.ultimaAtualizacao = LocalDateTime.now();
        validarDadosEspecificos();
    }

    public final void preencherDadosClinicosJPA(String historicoMedico, String alergias, LocalDateTime now) {
        this.historicoMedico = historicoMedico;
        this.alergias = alergias;
        this.ultimaAtualizacao = now;
    }

    protected abstract boolean validarDadosEspecificos();

    public final boolean validarDadosObrigatorios() {
        return historicoMedico != null && !historicoMedico.isBlank()
                && alergias != null && !alergias.isBlank()
                && validarDadosEspecificos();
    }

    public final void adicionarObservacaoJPA(String observacao, LocalDateTime now) {
        this.observacoes = observacao;
        this.ultimaAtualizacao = now;
    }

    protected abstract void processarObservacao(String observacao);

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

    public void setHistoricoMedico(String historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public long getId() {
        return id;
    }
}
