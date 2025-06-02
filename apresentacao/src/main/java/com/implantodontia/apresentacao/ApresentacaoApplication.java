package com.implantodontia.apresentacao;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaRepository;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteRepository;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaRepositorio;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.support.notificacoes.ConsoleNotificacaoObserver;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.NotificacaoProdutor;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.implantodontia.apresentacao",
        "com.implantodontia.dominio",
        "com.implantodontia.infraestrutura"})
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.implantodontia.infraestrutura.persistencia")
public class ApresentacaoApplication {

    @Bean
    public ConsultaService consultaService(ConsultaRepository repository, MaterialServico materialServico) {
        return new ConsultaService(repository,  materialServico);
    }

    @Bean
    public FichaMedicaServico fichaMedicaServico(FichaMedicaRepositorio fichaMedicaRepositorio) {
        return new FichaMedicaServico(fichaMedicaRepositorio);
    }

    @Bean
    public PacienteService pacienteService(PacienteRepository pacienteRepository, NotificacaoService notificacaoService){
        return new PacienteService(pacienteRepository, notificacaoService);
    }

    @Bean
    public NotificacaoService configurarNotificacaoService(ConsoleNotificacaoObserver consoleObserver, NotificacaoProdutor produtor, NotificacaoConsumidor consumidor) {
        NotificacaoService notificacaoService = new NotificacaoService(consumidor);

        // Registra o observer do console
        notificacaoService.registrarObserver(consoleObserver);
        notificacaoService.registrarObserver(produtor);

        // TODO: notificacaoService.registrarObserver(emailObserver);
        // TODO: notificacaoService.registrarObserver(smsObserver);

        return notificacaoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApresentacaoApplication.class, args);
    }

}
