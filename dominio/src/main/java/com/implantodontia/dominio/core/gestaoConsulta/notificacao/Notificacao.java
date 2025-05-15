package com.implantodontia.dominio.core.gestaoConsulta.notificacao;

import com.implantodontia.dominio.core.gestaoConsulta.notificacao.enums.TipoNotificacao;

import java.time.LocalDateTime;

public class Notificacao {
    private String destinatario;
    private String mensagem;
    private TipoNotificacao tipo;
    private LocalDateTime criacao;

    public Notificacao(String destinatario, String mensagem, TipoNotificacao tipo, LocalDateTime criacao) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.criacao = criacao;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCriacao() {
        return criacao;
    }

    public void setCriacao(LocalDateTime criacao) {
        this.criacao = criacao;
    }
}
