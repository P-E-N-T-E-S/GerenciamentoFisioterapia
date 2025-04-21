package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Endereco {
    String logradouro;
    String numero;
    String complemento;
    String cidade;
    String cep;

    public Endereco(String logradouro, String numero, String complemento, String cidade, String cep) {
        notNull(logradouro, "O logradouro não pode ser nulo");
        notNull(numero, "O número não pode ser nulo");
        notNull(cidade, "A cidade não pode ser nula");
        notNull(cep, "O CEP não pode ser nulo");
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
}