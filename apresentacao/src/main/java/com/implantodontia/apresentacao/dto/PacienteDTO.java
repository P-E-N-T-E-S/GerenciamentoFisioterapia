package com.implantodontia.apresentacao.dto;

public record PacienteDTO(String cpf, String nome, EnderecoDTO endereco, String email, String contato, String medicoResponsavel) {
}
