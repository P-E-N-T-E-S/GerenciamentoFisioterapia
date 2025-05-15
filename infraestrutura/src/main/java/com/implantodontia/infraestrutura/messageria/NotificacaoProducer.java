package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.Notificacao;
import com.implantodontia.dominio.core.gestaoConsulta.notificacao.NotificacaoProdutor;
import com.implantodontia.infraestrutura.messageria.dto.NotificacaoDTO;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoProducer implements NotificacaoProdutor {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(Notificacao notificacao) {
        NotificacaoDTO dto = NotificacaoMapper.toDTO(notificacao);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                dto
        );
    }
}

