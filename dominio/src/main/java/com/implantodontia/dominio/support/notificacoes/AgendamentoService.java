package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;

import java.time.LocalDate;
import java.util.List;

public class AgendamentoService {

    private ConsultaService consultaService;
    private NotificacaoService notificacaoService;

    public AgendamentoService(ConsultaService consultaService, NotificacaoService notificacaoService) {
        this.consultaService = consultaService;
        this.notificacaoService = notificacaoService;
    }

    public void gerarLembretesConsultas(){
        List<Consulta> consultaList = consultaService.buscarPorData(LocalDate.now());
        consultaList.forEach(consulta -> {
            notificacaoService.notificarUsuario("fisioterapeuta@gmail.com", "Você tem um procedimento pendente para o hoje de "+consulta.getPaciente().getNome(), TipoNotificacao.AGENDAMENTO);
        });
    }

    public void gerarLembretesPagamentos(){
        List<Consulta> consultaList = consultaService.buscarPorDataVencimento(LocalDate.now());
        consultaList.forEach(consulta -> {
            notificacaoService.notificarUsuario("fisioterapeuta@gmail.com", "O cliente: "+consulta.getPaciente().getNome()+" tem o vencimento do pagamento no dia: "+consulta.getDataVencimento()+" e ainda não foi registrado o pagamento", TipoNotificacao.PAGAMENTO);
            notificacaoService.notificarUsuario(consulta.getPaciente().getEmail(), "Bom dia, o pagamento para a consulta do dia: "+consulta.getDataHora()+" venceu no dia: "+consulta.getDataVencimento()+" e ainda não recebi seu pagamento", TipoNotificacao.PAGAMENTO_CLIENTE);
        });
    }
}
