package com.desafio.XBrain.venda.entity;

import com.desafio.XBrain.vendedor.entity.Vendedor;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Venda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_da_venda")
    private LocalDateTime dataDaVenda;
    private BigDecimal valor;
    @ManyToOne @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
    private String nomeCompletoVendedor;
    private Boolean ativo;
}
