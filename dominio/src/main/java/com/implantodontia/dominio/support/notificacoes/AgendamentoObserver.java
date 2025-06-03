package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoObserver implements NotificacaoObserver {
    
    @Override
    public void atualizarNotificacao(Notificacao notificacao) {
        // Aqui você pode implementar a lógica específica para lidar com notificações de agendamento
        // Por exemplo, enviar um lembrete por email ou SMS
        System.out.println("Notificação de agendamento recebida: " + notificacao.getMensagem());
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.AGENDAMENTO;
    }
} 