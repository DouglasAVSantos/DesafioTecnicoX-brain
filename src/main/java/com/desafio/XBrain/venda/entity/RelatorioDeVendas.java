package com.desafio.XBrain.venda.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioDeVendas {
    private Long id;
    private String nome;
    private Long quantidade;
    private BigDecimal media;

    private RelatorioDeVendas(Long id, String nome, Long quantidade){
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public RelatorioDeVendas addMedia(BigDecimal media){
       return new RelatorioDeVendas(this.id,this.nome,this.quantidade,media);
    }
}
