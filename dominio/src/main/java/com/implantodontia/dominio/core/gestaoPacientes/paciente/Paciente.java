package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import static org.apache.commons.lang3.Validate.notNull;

public class Paciente {
    private PacienteId pacienteId;
    private Cpf cpf;
    private Endereco endereco;
    private String nome;
    private String contato;
    private String medicoResponsavel;

    public Paciente(PacienteId pacienteId, Cpf cpf, Endereco endereco, String nome, String contato, String medicoResponsavel) {
        notNull(pacienteId, "O Id do paciente nao pode ser nulo");
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
    }

    public String getNome() { return nome; }
    public String getContato() { return contato; }
    public String getMedicoResponsavel() { return medicoResponsavel; }
}