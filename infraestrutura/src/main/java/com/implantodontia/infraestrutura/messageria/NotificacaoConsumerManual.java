package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.Notificacao;
import com.implantodontia.dominio.core.gestaoConsulta.notificacao.enums.TipoNotificacao;
import com.implantodontia.infraestrutura.messageria.dto.NotificacaoDTO;
import com.implantodontia.infraestrutura.messageria.mapper.NotificacaoMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoConsumerManual {

    private final RabbitTemplate rabbitTemplate;

    public NotificacaoConsumerManual(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void consumirMensagens(TipoNotificacao tipo) {
        Object obj;
        String fila = mapearFila(tipo);
        do {
            obj = rabbitTemplate.receiveAndConvert(fila);
            if (obj instanceof NotificacaoDTO dto) {
                Notificacao notificacao = NotificacaoMapper.toDomain(dto);
                System.out.println("Notificação do domínio: " + notificacao.getMensagem());
                // Aqui você pode repassar ao serviço de domínio
            }
        } while (obj != null);
    }

    private String mapearFila(TipoNotificacao tipo) {
        return switch (tipo) {
            case CLIENTE_NOVO -> RabbitMQConfig.FILA_NOVOS_CLIENTES;
            case AGENDAMENTO -> RabbitMQConfig.FILA_RELEMBRETE;
            case PAGAMENTO -> RabbitMQConfig.FILA_PAGAMENTO;
            default -> throw new IllegalArgumentException("Tipo de notificação não suportado: " + tipo);
        };
    }
}

