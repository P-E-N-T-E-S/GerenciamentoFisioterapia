package com.implantodontia.dominio.consulta.paciente;

import java.time.LocalDate;

public class Paciente {
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