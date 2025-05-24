package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;

public interface NotificacaoObserver {
    void atualizar(Notificacao notificacao);
    TipoNotificacao getTipoNotificacao();
} 