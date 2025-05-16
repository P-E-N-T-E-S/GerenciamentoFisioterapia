package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AgendamentoScheduler {

    private ConsultaService consultaService;
    private NotificacaoService notificacaoService;

    public AgendamentoScheduler(ConsultaService consultaService, NotificacaoService notificacaoService) {
        this.consultaService = consultaService;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void gerarLembretes(){
        List<Consulta> consultaList = consultaService.buscarPorData(LocalDate.now());
        consultaList.forEach(consulta -> {
            notificacaoService.notificarUsuario("fisioterapeuta@gmail.com", "Você tem um procedimento pendente para o hoje ", TipoNotificacao.AGENDAMENTO);
        });
    }
}
