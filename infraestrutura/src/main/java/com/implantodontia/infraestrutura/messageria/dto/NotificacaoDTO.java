package com.implantodontia.infraestrutura.messageria.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NotificacaoDTO implements Serializable {
    private String destinatario;
    private String mensagem;
    private String tipo;
    private LocalDateTime criacao;

    public NotificacaoDTO(String destinatario, String mensagem, String tipo, LocalDateTime criacao) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCriacao() {
        return criacao;
    }

    public void setCriacao(LocalDateTime criacao) {
        this.criacao = criacao;
    }

    @Override
    public String toString() {
        return "NotificacaoDTO{" +
                "destinatario='" + destinatario + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", tipo='" + tipo + '\'' +
                ", criacao=" + criacao +
                '}';
    }
}

