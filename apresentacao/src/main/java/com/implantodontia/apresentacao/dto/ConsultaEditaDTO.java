package com.implantodontia.apresentacao.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ConsultaEditaDTO(long id, LocalDateTime dataHora, long pacienteId, String descricao, EnderecoDTO endereco, double valor, List<MaterialDTO> materiaisUsados, LocalDate dataVencimento, boolean clientePagou) {
}
