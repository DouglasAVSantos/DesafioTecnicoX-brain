package com.desafio.XBrain.vendedor.entity;

import com.desafio.XBrain.venda.entity.Venda;
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
    @Column(name = "lista_de_vendas")
    private List<Venda> listaDeVendas = new ArrayList<>();
    private Boolean ativo;
}
