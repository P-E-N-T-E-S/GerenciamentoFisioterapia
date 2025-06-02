package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.support.notificacoes.AgendamentoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoScheduler {

    private AgendamentoService agendamentoService;

    public AgendamentoScheduler(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Scheduled(cron="0 0 6 * * *")
    public void agendarConsultas(){
        agendamentoService.gerarLembretesConsultas();
        agendamentoService.gerarLembretesPagamentos();
    }
}
