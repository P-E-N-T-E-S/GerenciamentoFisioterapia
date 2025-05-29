package com.implantodontia.dominio.support.notificacoes;

import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoService implements NotificacaoSubject {

    private final NotificacaoConsumidor consumer;
    private final List<NotificacaoObserver> observers;
    private Notificacao ultimaNotificacao;

    public NotificacaoService(NotificacaoConsumidor consumer) {
        this.consumer = consumer;
        this.observers = new ArrayList<>();
    }

    @Override
    public void registrarObserver(NotificacaoObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removerObserver(NotificacaoObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers() {
        if (ultimaNotificacao != null) {
            for (NotificacaoObserver observer : observers) {
                if (observer.getTipoNotificacao() == ultimaNotificacao.getTipo() || 
                    observer.getTipoNotificacao() == TipoNotificacao.TODAS) {
                    observer.atualizar(ultimaNotificacao);
                }
            }
        }
    }

    public void notificarUsuario(String destino, String mensagem, TipoNotificacao tipo) {
        Notificacao evento = new Notificacao(destino, mensagem, tipo, LocalDateTime.now());
        this.ultimaNotificacao = evento;
        notificarObservers();
    }

    public List<Notificacao> obterNotificacoes() {
        return consumer.consumirMensagens();
    }
}

