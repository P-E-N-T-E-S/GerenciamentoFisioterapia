package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoProdutor;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.messageria.config.RabbitMQConfig;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoProducer implements NotificacaoProdutor {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void atualizar(Notificacao notificacao) {
        NotificacaoDTO dto = NotificacaoMapper.toDTO(notificacao);
        String routingKey = RabbitMQConfig.TODAS_NOTIFICACOES_ROUTING_KEY;
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                routingKey,
                dto
        );
    }

    @Override
    public TipoNotificacao getTipoNotificacao() {
        return TipoNotificacao.TODAS;
    }
}

