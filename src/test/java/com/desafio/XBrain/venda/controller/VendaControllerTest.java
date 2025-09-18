package com.desafio.XBrain.venda.controller;

import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.entity.RelatorioDeVendas;
import com.desafio.XBrain.venda.entity.Venda;
import com.desafio.XBrain.venda.service.VendaService;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaControllerTest {

    @InjectMocks
    private VendaController vendaController;

    @Mock
    private VendaService vendaService;

    private VendaRequestDto vendaRequestDto;
    private VendaResponseDto vendaResponseDto;
    private Venda venda;
    private Vendedor vendedor;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor(1L, "Vendedor", "Teste");
        vendaRequestDto = new VendaRequestDto(BigDecimal.TEN, 1L);
        venda = new Venda(vendaRequestDto, vendedor);
        venda.setId(1L);
        vendaResponseDto = new VendaResponseDto(venda);
    }

    @Test
    void deveCadastrarVendaComSucesso() {
        when(vendaService.cadastrar(any(VendaRequestDto.class))).thenReturn(vendaResponseDto);

        ResponseEntity<VendaResponseDto> response = vendaController.cadastra(vendaRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());

        verify(vendaService).cadastrar(any(VendaRequestDto.class));
    }

    @Test
    void deveRetornarRelatorioDeVendas() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = inicio.plusDays(1);
        RelatorioDeVendas relatorio = new RelatorioDeVendas(1L, "Vendedor Teste", 10L);
        RelatorioDeVendasDto relatorioDto = new RelatorioDeVendasDto(relatorio);

        when(vendaService.getRelatorio(inicio, fim)).thenReturn(List.of(relatorioDto));

        ResponseEntity<List<RelatorioDeVendasDto>> response = vendaController.getRelatorio(inicio, fim);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendedor Teste", response.getBody().get(0).vendedor());

        verify(vendaService).getRelatorio(inicio, fim);
    }

    @Test
    void deveRetornarListaDeVendas() {
        when(vendaService.getLista()).thenReturn(List.of(vendaResponseDto));

        ResponseEntity<List<VendaResponseDto>> response = vendaController.getLista();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1L, response.getBody().get(0).id());

        verify(vendaService).getLista();
    }

    @Test
    void deveRetornarListaVaziaDeVendas() {
        when(vendaService.getLista()).thenReturn(Collections.emptyList());

        ResponseEntity<List<VendaResponseDto>> response = vendaController.getLista();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(vendaService).getLista();
    }

    @Test
    void deveBuscarVendaPorIdComSucesso() {
        when(vendaService.get(1L)).thenReturn(vendaResponseDto);

        ResponseEntity<VendaResponseDto> response = vendaController.get(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());

        verify(vendaService).get(1L);
    }

    @Test
    void deveDeletarVendaComSucesso() {
        doNothing().when(vendaService).cencela(1L);

        ResponseEntity<Void> response = vendaController.deleta(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(vendaService).cencela(1L);
    }

    @Test
    void deveAtualizarVendaComSucesso() {
        when(vendaService.atualiza(anyLong(), any(VendaRequestDto.class))).thenReturn(vendaResponseDto);

        ResponseEntity<VendaResponseDto> response = vendaController.atualiza(1L, vendaRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());

        verify(vendaService).atualiza(anyLong(), any(VendaRequestDto.class));
    }
}
