package com.implantodontia.infraestrutura.persistencia.core.gestaoconsulta;

import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPA;
import com.implantodontia.infraestrutura.persistencia.core.material.MaterialJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente.PacienteJPA;
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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "material_consulta",
            joinColumns = @JoinColumn(name = "id_consulta"),
            inverseJoinColumns = @JoinColumn(name = "nome_material")
    )
    private List<MaterialJPA> materiais;

    @ManyToOne
    @JoinColumn (name = "paciente_id")
    private PacienteJPA paciente;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name = "endereco_id")
    private EnderecoJPA endereco;


    public GestaoConsultaJPA() {}


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


    public PacienteJPA getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteJPA paciente) {
        this.paciente = paciente;
    }

    public List<MaterialJPA> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<MaterialJPA> materiais) {
        this.materiais = materiais;
    }

    public EnderecoJPA getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoJPA endereco) {
        this.endereco = endereco;
    }
}
