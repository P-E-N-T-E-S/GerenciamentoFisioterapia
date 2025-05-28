package com.implantodontia.apresentacao.dto;

import java.time.LocalDateTime;

public record ConsultaDTO(LocalDateTime dataHora, long pacienteId, String descricao, String local) {
}
