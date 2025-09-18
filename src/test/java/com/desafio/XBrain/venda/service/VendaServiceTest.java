package com.desafio.XBrain.venda.service;

import com.desafio.XBrain.shared.exception.NotFoundException;
import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.entity.RelatorioDeVendas;
import com.desafio.XBrain.venda.entity.Venda;
import com.desafio.XBrain.venda.repository.VendaRepository;
import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendedorService vendedorService;

    private Vendedor vendedor;
    private VendaRequestDto vendaRequestDto;
    private Venda venda;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor(1L, "Vendedor", "Teste");
        vendaRequestDto = new VendaRequestDto(BigDecimal.valueOf(123.123), 1L);
        venda = new Venda(vendaRequestDto, vendedor);
        venda.setId(1L);
    }

    @Test
    void deveCadastrarVendaComSucesso() {
        when(vendedorService.findById(1L)).thenReturn(vendedor);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        VendaResponseDto result = vendaService.cadastrar(vendaRequestDto);

        assertEquals(1L, result.id());
        assertEquals(BigDecimal.valueOf(123.123), result.valor());
        assertEquals("Vendedor Teste", result.nomeCompleto());

        verify(vendedorService).findById(1L);
        verify(vendaRepository).save(any(Venda.class));
    }

    @Test
    void deveRetornarListaDeVendas() {
        when(vendaRepository.findAllByCanceladaFalse()).thenReturn(List.of(venda));

        List<VendaResponseDto> result = vendaService.getLista();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());

        verify(vendaRepository).findAllByCanceladaFalse();
    }

    @Test
    void deveRetornarRelatorioComSucesso(){
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(5);

        when(vendaRepository.getRelatorio(inicio,fim)).thenReturn(List.of(new RelatorioDeVendas(1L,"Vendedor Teste",15L)));

        List<RelatorioDeVendasDto> result = vendaService.getRelatorio(inicio,fim);

        assertEquals(1,result.size());
        assertEquals("Vendedor Teste",result.get(0).vendedor());

        verify(vendaRepository).getRelatorio(inicio,fim);
    }

    @Test
    void deveLancarDateTimeExceptionSeDataInicioPosteriorADataFim(){
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.minusDays(5);

        DateTimeException ex = assertThrows(DateTimeException.class,()->vendaService.getRelatorio(inicio,fim));

        assertEquals(ex.getMessage(),"Data inicial informada é posterior a data final");

        verify(vendaRepository,never()).getRelatorio(inicio,fim);
    }


    @Test
    void deveBuscarVendaPorIdComSucesso() {
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.of(venda));

        VendaResponseDto result = vendaService.get(1L);

        assertEquals(1L, result.id());
        assertEquals("Vendedor Teste", result.nomeCompleto());

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
    }

    @Test
    void deveLancarNotFoundExceptionAoBuscarVenda() {
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> vendaService.get(1L));

        assertEquals("Venda não encontrada para o id: 1", ex.getMessage());

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
    }

    @Test
    void deveCancelarVendaComSucesso() {
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.of(venda));

        assertDoesNotThrow(() -> vendaService.cencela(1L));

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
        verify(vendaRepository).save(any(Venda.class));
    }

    @Test
    void deveLancarNotFoundExceptionAoCancelarVenda() {
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> vendaService.cencela(1L));

        assertEquals("Venda não encontrada para o id: 1", ex.getMessage());

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
        verify(vendaRepository, never()).save(any(Venda.class));
    }

    @Test
    void deveAtualizarVendaComSucesso() {
        VendaRequestDto requestAtualizada = new VendaRequestDto(BigDecimal.valueOf(321.321), 1L);
        Vendedor vendedorAtualizado = new Vendedor(new VendedorRequestDto("Vendedor","Atualizado"));
        vendedorAtualizado.setId(2L);
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.of(venda));
        when(vendedorService.findById(1L)).thenReturn(vendedorAtualizado);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        VendaResponseDto result = vendaService.atualiza(1L, requestAtualizada);

        assertEquals(1L, result.id());
        assertEquals(2L, result.vendedorId());
        assertEquals(BigDecimal.valueOf(321.321), result.valor());

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
        verify(vendedorService).findById(1L);
        verify(vendaRepository).save(any(Venda.class));
    }

    @Test
    void deveLancarNotFoundExceptionAoAtualizarVenda() {
        VendaRequestDto requestAtualizada = new VendaRequestDto(BigDecimal.TEN, 1L);
        when(vendaRepository.findByIdAndCanceladaFalse(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> vendaService.atualiza(1L, requestAtualizada));

        assertEquals("Venda não encontrada para o id: 1", ex.getMessage());

        verify(vendaRepository).findByIdAndCanceladaFalse(1L);
        verify(vendedorService, never()).findById(anyLong());
        verify(vendaRepository, never()).save(any(Venda.class));
    }
}