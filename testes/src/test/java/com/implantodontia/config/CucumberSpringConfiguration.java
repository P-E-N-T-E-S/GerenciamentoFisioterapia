package com.implantodontia.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.ServicoPacientes; // Importe as classes necessárias

@CucumberContextConfiguration
@ContextConfiguration(classes = {ServicoPacientes.class}) // Lista de classes a serem carregadas
public class CucumberSpringConfiguration {
}