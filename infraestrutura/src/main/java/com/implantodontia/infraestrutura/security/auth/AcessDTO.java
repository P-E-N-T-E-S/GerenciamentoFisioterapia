package com.implantodontia.infraestrutura.security.auth;

import com.implantodontia.infraestrutura.messageria.NotificacaoDTO;

import java.util.List;

public record AcessDTO(String token, List<NotificacaoDTO> notificacaoes) {}
