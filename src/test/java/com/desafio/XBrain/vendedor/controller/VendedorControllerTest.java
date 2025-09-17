package com.desafio.XBrain.vendedor.controller;

import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.dto.VendedorResponseDto;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendedorControllerTest {

    @Mock
    private VendedorService service;

    @InjectMocks
    private VendedorController controller;

    private VendedorRequestDto request;
    private VendedorResponseDto response;
    private Vendedor vendedor;

    @BeforeEach
    public void setUp() {
        request = new VendedorRequestDto("Douglas", "Santos");
        vendedor = new Vendedor(request);
        response = new VendedorResponseDto(vendedor);
    }

    @Test
    public void cadastraComSucesso() {
        when(service.cadastrar(request)).thenReturn(response);

        ResponseEntity<VendedorResponseDto> result = controller.cadastrar(request);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(result.getBody().nomeCompleto(), "douglas santos");

        verify(service).cadastrar(request);
    }

    @Test
    public void retornaVendedorComSucesso() {
        when(service.getVendedor(1L)).thenReturn(response);

        ResponseEntity<VendedorResponseDto> result = controller.getVendedor(1L);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().nomeCompleto(), "douglas santos");

        verify(service).getVendedor(1L);
    }

    @Test
    public void retornaListaDeVendedorComSucesso() {
        when(service.getLista()).thenReturn(List.of(response));

        ResponseEntity<List<VendedorResponseDto>> result = controller.getLista();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().get(0).nomeCompleto(), "douglas santos");
        assertEquals(result.getBody().size(), 1);

        verify(service).getLista();
    }

    @Test
    public void deletaVendedorComSucesso() {
        ResponseEntity<Void> result = assertDoesNotThrow(() -> controller.deletar(1L));

        assertEquals(result.getStatusCode(), HttpStatus.NO_CONTENT);

        verify(service).deletar(1L);
    }

    @Test
    public void atuializaVendedorComSucesso() {
        when(service.atualizar(1L, request)).thenReturn(response);

        ResponseEntity<VendedorResponseDto> result = controller.atualizar(1L, request);

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().nomeCompleto(), "douglas santos");

        verify(service).atualizar(1L, request);
    }

}
