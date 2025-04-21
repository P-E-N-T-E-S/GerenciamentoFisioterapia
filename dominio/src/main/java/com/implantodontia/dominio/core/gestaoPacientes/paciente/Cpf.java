package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Cpf {
    String codigo;

    public Cpf(String codigo) {
        notNull(codigo, "Codigo não pode ser nulo");
        notBlank(codigo, "Codigo não pode ser vazio");
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
