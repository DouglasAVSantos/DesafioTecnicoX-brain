package com.desafio.XBrain.venda.dto;

import com.desafio.XBrain.venda.entity.RelatorioDeVendas;

import java.math.BigDecimal;

public record RelatorioDeVendasDto(
        Long id,
        String vendedor,
        Long quantidade,
        BigDecimal media
) {
    public RelatorioDeVendasDto(RelatorioDeVendas vendaPorVendedor){
        this(vendaPorVendedor.getId(), vendaPorVendedor.getNome(), vendaPorVendedor.getQuantidade(), vendaPorVendedor.getMedia());
    }

}
