package com.desafio.XBrain.vendedor.dto;

import com.desafio.XBrain.vendedor.entity.Vendedor;
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

    public VendedorRequestDto(Vendedor vendedor) {
        this(vendedor.getNome(), vendedor.getSobrenome());
    }

    public String nomeCompleto() {
        return nome + " " + sobrenome;
    }
}
