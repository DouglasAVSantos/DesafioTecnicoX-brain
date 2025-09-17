package com.desafio.XBrain.vendedor.dto;

import jakarta.validation.constraints.NotBlank;

public record VendedorRequestDto(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        String sobrenome

) {

    public VendedorRequestDto {
        nome = nome.trim().toLowerCase();
        sobrenome = sobrenome.trim().toLowerCase();
    }

    public String nomeCompleto() {
        return nome + " " + sobrenome;
    }
}
