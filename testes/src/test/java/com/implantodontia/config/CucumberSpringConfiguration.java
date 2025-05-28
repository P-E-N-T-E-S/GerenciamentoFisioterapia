package com.implantodontia.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService; // Importe as classes necessárias

@CucumberContextConfiguration
@ContextConfiguration(classes = {PacienteService.class}) // Lista de classes a serem carregadas
public class CucumberSpringConfiguration {
}