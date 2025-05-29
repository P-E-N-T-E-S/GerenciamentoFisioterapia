package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.messageria.config.RabbitMQConfig;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificacaoConsumerManual implements NotificacaoConsumidor {

    private final RabbitTemplate rabbitTemplate;

    // Injeção do MessageConverter opcional (caso queira garantir no construtor)
    public NotificacaoConsumerManual(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(messageConverter);
    }

    public List<Notificacao> consumirMensagens() {
        Object obj;
        String fila = RabbitMQConfig.FILA_NOTIFICACOES;
        List<Notificacao> notificacoes = new java.util.ArrayList<>(List.of());
        do {
            obj = rabbitTemplate.receiveAndConvert(fila);
            if (obj instanceof NotificacaoDTO dto) {
                Notificacao notificacao = NotificacaoMapper.toDomain(dto);
                notificacoes.add(notificacao);
            }
        } while (obj != null);
        return notificacoes;
    }

}

