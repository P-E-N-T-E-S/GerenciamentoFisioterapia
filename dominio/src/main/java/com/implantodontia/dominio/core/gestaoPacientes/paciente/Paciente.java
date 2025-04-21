package com.implantodontia.dominio.core.gestaoPacientes.paciente;

public class Paciente {
    private PacienteId pacienteId;
    private Cpf cpf;
    private Endereco endereco;
    private String nome;
    private String contato;
    private String medicoResponsavel;

    public Paciente(String nome, String contato, String medicoResponsavel) {
        this.nome = nome;
        this.contato = contato;
        this.medicoResponsavel = medicoResponsavel;
    }

    public String getNome() { return nome; }
    public String getContato() { return contato; }
    public String getMedicoResponsavel() { return medicoResponsavel; }
}