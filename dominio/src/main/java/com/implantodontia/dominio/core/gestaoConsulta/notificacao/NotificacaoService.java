package com.implantodontia.dominio.core.gestaoConsulta.notificacao;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.enums.TipoNotificacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacaoService {

    private final NotificacaoProdutor producer;

    public NotificacaoService(NotificacaoProdutor producer) {
        this.producer = producer;
    }

    public void notificarUsuario(String destino, String mensagem, TipoNotificacao tipo) {
        Notificacao evento = new Notificacao(destino, mensagem, tipo, LocalDateTime.now());
        producer.enviar(evento);
    }
}

