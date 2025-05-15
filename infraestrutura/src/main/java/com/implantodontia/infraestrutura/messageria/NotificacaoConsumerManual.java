package com.implantodontia.infraestrutura.messageria;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.Notificacao;
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

    public void consumirMensagens() {
        Object obj;
        do {
            obj = rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE);
            if (obj instanceof NotificacaoDTO dto) {
                Notificacao notificacao = NotificacaoMapper.toDomain(dto);
                System.out.println("Notificação do domínio: " + notificacao.getMensagem());
                // Aqui você pode repassar ao serviço de domínio
            }
        } while (obj != null);
    }
}

