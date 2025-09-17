package com.desafio.XBrain.vendedor.service;

import com.desafio.XBrain.shared.exception.ConflictException;
import com.desafio.XBrain.shared.exception.NotFoundException;
import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.dto.VendedorResponseDto;
import com.desafio.XBrain.vendedor.entity.Vendedor;
import com.desafio.XBrain.vendedor.repository.VendedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendedorServiceTest {

    @InjectMocks
    private VendedorService service;
    @Mock
    private VendedorRepository repository;
    private VendedorRequestDto request;
    private VendedorResponseDto response;
    private Vendedor vendedor;

    @BeforeEach
    void setUp() {
        request = new VendedorRequestDto("Douglas", "Santos");
        vendedor = new Vendedor(request);
        vendedor.setId(1L);
        response = new VendedorResponseDto(vendedor);
    }

    @Test
    void deveCadastrarComSucesso() {
        when(repository.findByNomeAndSobrenomeAndAtivoTrue(request.nome(), request.sobrenome())).thenReturn(Optional.empty());
        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        VendedorResponseDto result = service.cadastrar(request);

        assertEquals(result.nome(), "douglas");
        assertEquals(result.sobrenome(), "santos");
        assertEquals(result.id(), 1);

        verify(repository).findByNomeAndSobrenomeAndAtivoTrue(request.nome(), request.sobrenome());
        verify(repository).save(any(Vendedor.class));
    }

    @Test
    void deveLancarConflictExceptionAoCadastrar() {
        when(repository.findByNomeAndSobrenomeAndAtivoTrue(request.nome(), request.sobrenome())).thenReturn(Optional.of(vendedor));

        ConflictException ex = assertThrows(ConflictException.class,
                () -> service.cadastrar(request));

        assertEquals(ex.getMessage(), "Vendedor '" + request.nomeCompleto() + "' já cadastrado");

        verify(repository).findByNomeAndSobrenomeAndAtivoTrue(request.nome(), request.sobrenome());
        verify(repository, never()).save(any(Vendedor.class));
    }

    @Test
    void deveBuscarVendedorComSucesso() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(vendedor));

        VendedorResponseDto result = service.getVendedor(1L);

        assertEquals(1L, result.id());
        assertEquals("douglas", result.nome());
        assertEquals("santos", result.sobrenome());
        assertEquals("douglas santos", result.nomeCompleto());

        verify(repository).findByIdAndAtivoTrue(1L);
    }

    @Test
    void deveLancarNotFoundExceptionAoBuscarVendedor() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> service.getVendedor(1L));

        assertEquals("Vendedor não encontrado para o id: 1", result.getMessage());

        verify(repository).findByIdAndAtivoTrue(1L);
    }

    @Test
    void deveRetornarListaComSucesso() {
        Vendedor vendedorTeste = new Vendedor(2L, "marcos", "silva", "marcos silva", true);
        List<Vendedor> listaTeste = List.of(vendedor, vendedorTeste);
        when(repository.findAllByAtivoTrue()).thenReturn(listaTeste);

        List<VendedorResponseDto> listaResult = service.getLista();

        assertEquals(2, listaResult.size());
        assertEquals(listaResult.get(0).nomeCompleto(), "douglas santos");
        assertEquals(listaResult.get(1).nomeCompleto(), "marcos silva");

        verify(repository).findAllByAtivoTrue();
    }

    @Test
    void deveDeletarVendedorComSucesso() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(vendedor));
        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);
        assertDoesNotThrow(() -> service.deletar(1L));


        verify(repository).findByIdAndAtivoTrue(1L);
        verify(repository).save(any(Vendedor.class));
    }

    @Test
    void deveLancarNoFoundExceptionAoDeletarVendedor() {
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> service.deletar(1L));

        assertEquals("Vendedor não encontrado para o id: 1", result.getMessage());

        verify(repository).findByIdAndAtivoTrue(1L);
        verify(repository, never()).save(any(Vendedor.class));
    }

    @Test
    void deveAtualizarComSucesso() {
        VendedorRequestDto atualizado = new VendedorRequestDto("marcos", "silva");
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(vendedor));
        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        VendedorResponseDto result = service.atualizar(1L, atualizado);

        assertEquals(result.nomeCompleto(), "marcos silva");
        assertEquals(result.id(), 1);

        verify(repository).findByIdAndAtivoTrue(1L);
        verify(repository).save(any(Vendedor.class));
    }

    @Test
    void deveLancarNoFoundExceptionAoAtualizarVendedor() {
        VendedorRequestDto atualizado = new VendedorRequestDto("marcos", "silva");
        when(repository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> service.atualizar(1L, atualizado));

        assertEquals("Vendedor não encontrado para o id: 1", result.getMessage());

        verify(repository).findByIdAndAtivoTrue(1L);
        verify(repository, never()).save(any(Vendedor.class));
    }


}
