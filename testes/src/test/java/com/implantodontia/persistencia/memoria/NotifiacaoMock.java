package com.implantodontia.persistencia.memoria;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;

import java.util.ArrayList;
import java.util.List;

public class NotifiacaoMock {

        private Repositorio repositorio;

    public NotifiacaoMock(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void atualizarNotificacao(Notificacao notificacao) {
        repositorio.atualizarNotificacao(notificacao);
    }

    public TipoNotificacao getTipoNotificacao() {
        return repositorio.getTipoNotificacao();
    }

    public void limparNotificacao(){
        repositorio.limparNotificacao();
    }

    public List<Notificacao> displayNotificacao(){
        return repositorio.displayNotificacao();
    }
}
