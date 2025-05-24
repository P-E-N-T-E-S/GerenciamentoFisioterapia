package com.implantodontia.infraestrutura.persistencia.core.paciente;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class PacienteJPA {
    @Id
    long id;

}
