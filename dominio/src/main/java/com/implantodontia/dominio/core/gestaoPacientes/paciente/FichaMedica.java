package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import java.time.LocalDateTime;

public class FichaMedica {
    private final PacienteId pacienteId;
    private String historicoMedico;
    private String alergias;
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;

    public FichaMedica(PacienteId pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void preencherDadosClinicos(String historicoMedico, String alergias, LocalDateTime now) {
        this.historicoMedico = historicoMedico;
        this.alergias = alergias;
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    public boolean validarDadosObrigatorios(){
        return historicoMedico != null && !historicoMedico.isBlank()
                && alergias != null && !alergias.isBlank();
    }


    public void adicionarObservacao(String observacao) {
        this.observacoes = observacao;
        this.ultimaAtualizacao = LocalDateTime.now();
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

}
