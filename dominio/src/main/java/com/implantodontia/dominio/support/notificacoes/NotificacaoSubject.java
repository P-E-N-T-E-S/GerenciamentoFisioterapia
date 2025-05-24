package com.implantodontia.dominio.support.notificacoes;

public interface NotificacaoSubject {
    void registrarObserver(NotificacaoObserver observer);
    void removerObserver(NotificacaoObserver observer);
    void notificarObservers();
} 