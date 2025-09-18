package com.desafio.XBrain.venda.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record VendaRequestDto(
        @DecimalMin(value = "0.01", message = "valor deve ser maior que 0.01")
        @NotNull(message = "Campo Obrigatório")
        BigDecimal valor,
        @NotNull(message = "Campo Obrigatório")
        Long idVendedor
) {

}
