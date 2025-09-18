package com.desafio.XBrain.venda.entity;

import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_da_venda")
    private LocalDate dataDaVenda;
    private BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
    private Boolean ativo;

    public Venda(VendaRequestDto requestVenda, Vendedor vendedor) {
        this.dataDaVenda = LocalDate.now();
        this.valor = requestVenda.valor();
        this.vendedor = vendedor;
        this.ativo = true;
    }
}
