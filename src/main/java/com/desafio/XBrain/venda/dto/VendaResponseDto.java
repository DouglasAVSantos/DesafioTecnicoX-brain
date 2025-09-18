package com.desafio.XBrain.venda.dto;

import com.desafio.XBrain.venda.entity.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VendaResponseDto(
        Long id,
        LocalDate data,
        BigDecimal valor,
        Long vendedorId,
        String nomeCompleto
) {

    public VendaResponseDto(Venda venda) {
        this(venda.getId()
                , venda.getDataDaVenda()
                , venda.getValor()
                , venda.getVendedor().getId()
                , venda.getVendedor().getNomeCompleto());
    }
}
