package com.implantodontia.apresentacao.controllers;

import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private NotificacaoConsumidor notificacaoConsumidor;

    public NotificacaoController(NotificacaoConsumidor notificacaoConsumidor) {
        this.notificacaoConsumidor = notificacaoConsumidor;
    }

    @GetMapping()
    public ResponseEntity<List<Notificacao>> obterTodasNotificacoes() {
        return ResponseEntity.ok(notificacaoConsumidor.consumirMensagens(TipoNotificacao.TODAS));
    }
}
