package com.implantodontia.infraestrutura.messageria.mapper;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.Notificacao;
import com.implantodontia.infraestrutura.messageria.dto.NotificacaoDTO;

public class NotificacaoMapper {

    public static Notificacao toDomain(NotificacaoDTO dto) {
        return new Notificacao(dto.getDestinatario(), dto.getMensagem(), dto.getTipo(), dto.getCriacao());
    }

    public static NotificacaoDTO toDTO(Notificacao notificacao) {
        return new NotificacaoDTO(notificacao.getDestinatario(), notificacao.getMensagem(), notificacao.getTipo(), notificacao.getCriacao());
    }
}

