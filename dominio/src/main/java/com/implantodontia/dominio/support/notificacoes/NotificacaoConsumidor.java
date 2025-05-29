package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;

import java.util.List;

public interface NotificacaoConsumidor {
    List<Notificacao> consumirMensagens();
}
