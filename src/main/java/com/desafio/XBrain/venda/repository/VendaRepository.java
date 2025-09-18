package com.desafio.XBrain.venda.repository;

import com.desafio.XBrain.venda.entity.RelatorioDeVendas;
import com.desafio.XBrain.venda.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    Optional<Venda> findByIdAndCanceladaFalse(Long id);

    List<Venda> findAllByCanceladaFalse();

    @Query(value = """
                select
                vend.id,
                 vend.nome_completo,
                  count(v.id)
                from vendas v
                join vendedores vend on vend.id = v.vendedor_id
                where v.cancelada = false
                  and v.data_da_venda between :inicio and :fim
                group by vend.id, vend.nome_completo
                order by vend.id
            """, nativeQuery = true)
    List<RelatorioDeVendas> getLista(@Param("inicio") LocalDate inicio
            , @Param("fim") LocalDate fim);
}
