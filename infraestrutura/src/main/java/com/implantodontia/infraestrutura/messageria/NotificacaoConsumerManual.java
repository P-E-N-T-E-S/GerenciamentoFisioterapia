package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.messageria.dto.NotificacaoDTO;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificacaoConsumerManual implements NotificacaoConsumidor {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoConsumerManual(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Notificacao> consumirMensagens(TipoNotificacao tipo) {
        Object obj;
        String fila = mapearFila(tipo);
        List<Notificacao> notificacoes = new java.util.ArrayList<>(List.of());
        do {
            obj = rabbitTemplate.receiveAndConvert(fila);
            if (obj instanceof NotificacaoDTO dto) {
                Notificacao notificacao = NotificacaoMapper.toDomain(dto);
                notificacoes.add(notificacao);
                // Aqui você pode repassar ao serviço de domínio
            }
        } while (obj != null);
        return notificacoes;
    }

    private String mapearFila(TipoNotificacao tipo) {
        return switch (tipo) {
            case CLIENTE_NOVO -> RabbitMQConfig.FILA_NOVOS_CLIENTES;
            case AGENDAMENTO -> RabbitMQConfig.FILA_RELEMBRETE;
            case PAGAMENTO -> RabbitMQConfig.FILA_PAGAMENTO;
            case TODAS -> RabbitMQConfig.FILA_TODAS_NOTIFICACOES;
            default -> throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        };
    }
}

