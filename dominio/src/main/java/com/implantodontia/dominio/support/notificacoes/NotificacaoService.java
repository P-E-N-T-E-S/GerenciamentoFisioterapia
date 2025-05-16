package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoProdutor producer;
    private final NotificacaoConsumidor consumer;

    public NotificacaoService(NotificacaoProdutor producer, NotificacaoConsumidor consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    public void notificarUsuario(String destino, String mensagem, TipoNotificacao tipo) {
        Notificacao evento = new Notificacao(destino, mensagem, tipo, LocalDateTime.now());
        producer.enviar(evento);
    }

    public List<Notificacao> obterNotificacoes(TipoNotificacao tipo){
        return consumer.consumirMensagens(tipo);
    }
}

