package com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.paciente;

import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.fichamedica.FichaMedicaJPA;
import jakarta.persistence.*;

@Entity
@Table(name = "paciente")
public class PacienteJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;
    private String email;
    private String nome;
    private String contato;
    private String medicoResponsavel;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name = "endereco_id")
    private EnderecoJPA endereco;

    @OneToOne(mappedBy = "paciente", orphanRemoval = true)
    private FichaMedicaJPA fichaMedica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public void setMedicoResponsavel(String medicoResponsavel) {
        this.medicoResponsavel = medicoResponsavel;
    }

    public FichaMedicaJPA getFichaMedica() {
        return fichaMedica;
    }

    public void setFichaMedica(FichaMedicaJPA fichaMedica) {
        this.fichaMedica = fichaMedica;
    }

    public EnderecoJPA getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoJPA endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
