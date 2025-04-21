package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import static org.springframework.util.Assert.notNull;

public class PacienteId {
    long id;

    public PacienteId(long id) {
        notNull(id, "O id do paciente não pode ser nulo");
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
