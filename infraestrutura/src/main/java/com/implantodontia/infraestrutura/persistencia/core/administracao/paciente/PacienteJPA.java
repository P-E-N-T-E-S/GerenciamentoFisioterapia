package com.implantodontia.infraestrutura.persistencia.core.administracao.paciente;

import com.implantodontia.infraestrutura.persistencia.core.gestao.gestaopaciente.FichaMedicaJPA;
import jakarta.persistence.*;

@Entity
@Table(name = "paciente")
public class PacienteJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;
    private String nome;
    private String contato;
    private String medicoResponsavel;

    // Endereço
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String cep;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public FichaMedicaJPA getFichaMedica() {
        return fichaMedica;
    }

    public void setFichaMedica(FichaMedicaJPA fichaMedica) {
        this.fichaMedica = fichaMedica;
    }
}
