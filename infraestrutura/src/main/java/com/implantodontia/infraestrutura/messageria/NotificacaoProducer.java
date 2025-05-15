package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.Notificacao;
import com.implantodontia.dominio.core.gestaoConsulta.notificacao.NotificacaoProdutor;
import com.implantodontia.dominio.core.gestaoConsulta.notificacao.enums.TipoNotificacao;
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
        String routingKey = mapearRoutingKey(notificacao.getTipo());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                routingKey,
                dto
        );
    }

    private String mapearRoutingKey(TipoNotificacao tipo) {
        return switch (tipo) {
            case CLIENTE_NOVO -> RabbitMQConfig.NOVOS_CLIENTES_ROUTING_KEY;
            case AGENDAMENTO -> RabbitMQConfig.RELEMBRETE_ROUTING_KEY;
            case PAGAMENTO -> RabbitMQConfig.PAGAMENTOS_ROUTING_KEY;
            default -> throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        };
    }
}

