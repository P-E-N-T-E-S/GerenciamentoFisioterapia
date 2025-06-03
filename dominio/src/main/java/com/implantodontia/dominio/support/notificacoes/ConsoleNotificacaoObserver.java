package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotificacaoObserver implements NotificacaoObserver {
    
    @Override
    public void atualizarNotificacao(Notificacao notificacao) {
        System.out.println("=== Nova Notificação ===");
        System.out.println("Destinatário: " + notificacao.getDestinatario());
        System.out.println("Mensagem: " + notificacao.getMensagem());
        System.out.println("Tipo: " + notificacao.getTipo());
        System.out.println("Data/Hora: " + notificacao.getCriacao());
        System.out.println("=====================");
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        // Retorna TODAS para receber todos os tipos de notificação
        return TipoNotificacao.TODAS;
    }
} 