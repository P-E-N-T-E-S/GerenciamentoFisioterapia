package com.implantodontia.config;

import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.dominio.support.relatorio.procedimento.HistoricoProcedimentosService;
import com.implantodontia.persistencia.memoria.Repositorio;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService; // Importe as classes necessárias

@CucumberContextConfiguration
@ContextConfiguration(classes = {HistoricoProcedimentosService.class, PacienteService.class, Repositorio.class, NotificacaoService.class}) // Lista de classes a serem carregadas
public class CucumberSpringConfiguration {
}