package com.desafio.XBrain.vendedor.dto;

import com.desafio.XBrain.vendedor.entity.Vendedor;

public record VendedorResponseDto(
        Long id,
        String nome,
        String sobrenome
) {
    public String nomeCompleto() {
        return nome + " " + sobrenome;
    }

    public VendedorResponseDto(Vendedor vendedor) {
        this(vendedor.getId(), vendedor.getNome(), vendedor.getSobrenome());
    }
}
