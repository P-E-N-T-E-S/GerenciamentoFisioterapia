package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Component;

@Component
public class PagamentoObserver implements NotificacaoObserver {
    
    @Override
    public void atualizar(Notificacao notificacao) {
        // Aqui você pode implementar a lógica específica para lidar com notificações de pagamento
        // Por exemplo, enviar um email, SMS, ou atualizar o status no sistema
        System.out.println("Notificação de pagamento recebida: " + notificacao.getMensagem());
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.PAGAMENTO;
    }
} 