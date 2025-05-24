package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente;

import com.implantodontia.infraestrutura.persistencia.core.administracao.paciente.PacienteJPA;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ficha_medica")
public class FichaMedicaJPA {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private PacienteJPA paciente;

    private String historicoMedico;
    private String alergias;
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistoricoMedico() {
        return historicoMedico;
    }

    public void setHistoricoMedico(String historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public PacienteJPA getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteJPA paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}
