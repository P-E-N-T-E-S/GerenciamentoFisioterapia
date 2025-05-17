package com.implantodontia.dominio.support.notificacoes.enums;

public enum TipoNotificacao {
    CLIENTE_NOVO("cliente novo"),
    AGENDAMENTO("agendamento"),
    PAGAMENTO("pagamento"),
    TODAS("todas");

    private final String tipo;

    TipoNotificacao(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return this.tipo;
    }

    public static TipoNotificacao fromString(String tipo) {
        for(TipoNotificacao t : TipoNotificacao.values()) {
            if(t.tipo.equals(tipo)) {
                return t;
            }
        }
        return null;
    }
}
