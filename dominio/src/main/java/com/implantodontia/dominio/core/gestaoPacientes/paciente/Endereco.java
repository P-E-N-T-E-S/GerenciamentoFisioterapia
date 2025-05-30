package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Endereco {
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String cep;

    public Endereco(String nome, String logradouro, String numero, String complemento, String cidade, String cep) {
        notNull(logradouro, "O logradouro não pode ser nulo");
        notNull(numero, "O número não pode ser nulo");
        notNull(cidade, "A cidade não pode ser nula");
        notNull(cep, "O CEP não pode ser nulo");
        this.nome = nome;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro) && Objects.equals(numero, endereco.numero) && Objects.equals(complemento, endereco.complemento) && Objects.equals(cidade, endereco.cidade) && Objects.equals(cep, endereco.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, complemento, cidade, cep);
    }

    public String getNome() {
        return nome;
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
}