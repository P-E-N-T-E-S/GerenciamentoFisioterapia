package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaoconsulta;

import com.implantodontia.infraestrutura.persistencia.core.administracao.material.MaterialJPA;
import com.implantodontia.infraestrutura.persistencia.core.administracao.paciente.PacienteJPA;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "consultas")
public class GestaoConsultaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private String descricao;
    private boolean clientePagou;
    private LocalDate dataVencimento;
    private String local;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private MaterialJPA materiais;

    @ManyToOne
    @JoinColumn (name = "paciente_id")
    private PacienteJPA paciente;


    public GestaoConsultaJPA() {}

    public GestaoConsultaJPA(Long id, LocalDateTime dataHora, String descricao, LocalDate dataVencimento, boolean clientePagou, String local, PacienteJPA pacientes, MaterialJPA materials) {
        this.id = id;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.clientePagou = clientePagou;
        this.local = local;
        this.paciente = pacientes;
        this.materiais = materials;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isClientePagou() {
        return clientePagou;
    }

    public void setClientePagou(boolean clientePagou) {
        this.clientePagou = clientePagou;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public PacienteJPA getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteJPA paciente) {
        this.paciente = paciente;
    }

    public MaterialJPA getMateriais() {
        return materiais;
    }

    public void setMateriais(MaterialJPA materiais) {
        this.materiais = materiais;
    }
}
