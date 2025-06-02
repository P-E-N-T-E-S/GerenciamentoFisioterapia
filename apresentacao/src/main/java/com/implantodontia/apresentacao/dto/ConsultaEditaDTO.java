package com.implantodontia.apresentacao.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultaEditaDTO(LocalDateTime dataHora, long pacienteId, String descricao, EnderecoDTO endereco, double valor, List<MaterialDTO> materiaisUsados) {
}
