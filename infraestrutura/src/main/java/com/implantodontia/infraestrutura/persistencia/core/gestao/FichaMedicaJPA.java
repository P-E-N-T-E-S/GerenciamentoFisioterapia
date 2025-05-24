package com.implantodontia.infraestrutura.persistencia.core.gestao;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.infraestrutura.persistencia.core.administracao.usuario.UsuarioJPA;
import com.implantodontia.infraestrutura.persistencia.core.paciente.PacienteJPA;
import jakarta.persistence.*;

@Entity
@Table(name = "ficha_medica")
public class FichaMedicaJPA {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private PacienteJPA paciente;

    private String historicoMedico;
    private String alergias;
    private String observacoes;

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
}
