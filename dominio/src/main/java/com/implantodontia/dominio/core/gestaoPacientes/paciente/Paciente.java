package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;

import static org.apache.commons.lang3.Validate.notNull;

public class Paciente {
    private PacienteId pacienteId;
    private String email;
    private Cpf cpf;
    private Endereco endereco;
    private String nome;
    private String contato;
    private String medicoResponsavel;
    private FichaMedica fichaMedica;


    public Paciente(PacienteId pacienteId, Cpf cpf, Endereco endereco, String nome, String contato, String medicoResponsavel, String email) {
        notNull(cpf, "O CPF do paciente nao pode ser nulo");
        notNull(endereco, "O endereco do paciente nao pode ser nulo");
        notNull(nome, "O nome do paciente nao pode ser nulo");
        notNull(contato, "O contato do paciente nao pode ser nulo");
        notNull(medicoResponsavel, "O medico responsavel nao pode ser nulo");

        this.pacienteId = pacienteId;
        this.cpf = cpf;
        this.endereco = endereco;
        this.nome = nome;
        this.contato = contato;
        this.medicoResponsavel = medicoResponsavel;
        this.email = email;
    }

    public PacienteId getPacienteId() {
        return pacienteId;
    }

    public FichaMedica getFichaMedica() {
        return fichaMedica;
    }

    public void vincularFichaMedica(FichaMedica ficha) {
        if (!(ficha.getPacienteId().getId() == (this.pacienteId.getId()))) {
            throw new IllegalArgumentException("ID da ficha não corresponde ao paciente!");
        }
        this.fichaMedica = ficha;
    }

    public String getNome() { return nome; }
    public String getContato() { return contato; }
    public String getMedicoResponsavel() { return medicoResponsavel; }

    public void setPacienteId(PacienteId pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public void setMedicoResponsavel(String medicoResponsavel) {
        this.medicoResponsavel = medicoResponsavel;
    }

    public void setFichaMedica(FichaMedica fichaMedica) {
        this.fichaMedica = fichaMedica;
    }

    public String getEmail() {
        return email;
    }
}