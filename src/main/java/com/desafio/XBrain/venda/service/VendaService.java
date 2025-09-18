package com.desafio.XBrain.venda.service;

import com.desafio.XBrain.shared.exception.NotFoundException;
import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.entity.Venda;
import com.desafio.XBrain.venda.repository.VendaRepository;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.service.VendedorService;
import jakarta.transaction.Transactional;
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

    @Transactional
    public VendaResponseDto cadastrar(VendaRequestDto request) {
        Vendedor vendedor = vendedorService.findById(request.idVendedor());
        Venda venda = repository.save(new Venda(request, vendedor));
        return new VendaResponseDto(venda);
    }

    public List<RelatorioDeVendasDto> getRelatorio(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new DateTimeException("Data inicial informada é posterior a data final");
        }
        Long diferencaDeDias = Math.max(1, ChronoUnit.DAYS.between(inicio, fim));
        return repository.getRelatorio(inicio, fim)
                .stream()
                .map(object ->
                        new RelatorioDeVendasDto(object.addMedia(
                                BigDecimal.valueOf(object.getQuantidade())
                                        .divide(BigDecimal.valueOf(diferencaDeDias), 4, RoundingMode.HALF_UP))))
                .toList();
    }

    public List<VendaResponseDto> getLista() {
        return repository.findAllByCanceladaFalse().stream().map(VendaResponseDto::new).toList();
    }

    public VendaResponseDto get(Long id) {
        return new VendaResponseDto(this.find(id));
    }

    @Transactional
    public void cencela(Long id) {
        Venda vendaCancelada = this.find(id);
        vendaCancelada.setCancelada(true);
        repository.save(vendaCancelada);
    }

    @Transactional
    public VendaResponseDto atualiza(Long id, VendaRequestDto dto) {
        Venda vendaAtualizada = this.find(id);
        Vendedor vendedorAtualizado = vendedorService.findById(dto.idVendedor());
        vendaAtualizada.setValor(dto.valor());
        vendaAtualizada.setVendedor(vendedorAtualizado);
        return new VendaResponseDto(repository.save(vendaAtualizada));
    }

    public Venda find(Long id) {
        return repository.findByIdAndCanceladaFalse(id).orElseThrow(() -> new NotFoundException("Venda não encontrada para o id: " + id));
    }

}
