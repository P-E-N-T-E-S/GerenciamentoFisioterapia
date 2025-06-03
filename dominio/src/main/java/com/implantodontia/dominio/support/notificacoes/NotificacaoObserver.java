package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;

public interface NotificacaoObserver {
    void atualizarNotificacao(Notificacao notificacao);
    TipoNotificacao getTipoNotificacao();
} 