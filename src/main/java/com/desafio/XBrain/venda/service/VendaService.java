package com.desafio.XBrain.venda.service;

import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.entity.Venda;
import com.desafio.XBrain.venda.repository.VendaRepository;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository repository;
    private final VendedorService vendedorService;

    public VendaResponseDto cadastrar(VendaRequestDto request) {
        Vendedor vendedor = vendedorService.findById(request.idVendedor());
        Venda venda = repository.save(new Venda(request, vendedor));
        return new VendaResponseDto(venda);
    }

    public List<RelatorioDeVendasDto> getRelatorio(LocalDate inicio, LocalDate fim) {
        if(inicio.isAfter(fim)){
            throw new DateTimeException("Data inicial informada é após a data final");
        }
        Long diferencaDeDias = Math.max(1, ChronoUnit.DAYS.between(inicio, fim));
        return repository.getLista(inicio, fim)
                .stream()
                .map(object ->
                        new RelatorioDeVendasDto(object.addMedia(
                                BigDecimal.valueOf(object.getQuantidade())
                                        .divide(BigDecimal.valueOf(diferencaDeDias), 4, RoundingMode.HALF_UP))))
                .toList();
    }

}
