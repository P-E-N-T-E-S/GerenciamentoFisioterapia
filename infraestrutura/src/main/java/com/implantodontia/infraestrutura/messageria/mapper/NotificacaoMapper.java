package com.implantodontia.infraestrutura.messageria.mapper;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.messageria.NotificacaoDTO;

public class NotificacaoMapper {

    public static Notificacao toDomain(NotificacaoDTO dto) {
        return new Notificacao(dto.getDestinatario(), dto.getMensagem(), TipoNotificacao.fromString(dto.getTipo()), dto.getCriacao());
    }

    public static NotificacaoDTO toDTO(Notificacao notificacao) {
        return new NotificacaoDTO(notificacao.getDestinatario(), notificacao.getMensagem(), notificacao.getTipo().toString(), notificacao.getCriacao());
    }
}

