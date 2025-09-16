package com.desafio.XBrain.vendedor.entity;

import com.desafio.XBrain.venda.entity.Venda;
import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendedores")
@EqualsAndHashCode(of = "id")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;
    @Column(name = "nome_completo")
    private String nomeCompleto;
    private Boolean ativo;

    public Vendedor(VendedorRequestDto dto){
        nome = dto.nome();
        sobrenome = dto.sobrenome();
        nomeCompleto = dto.nomeCompleto();
        ativo = true;
    }
}
