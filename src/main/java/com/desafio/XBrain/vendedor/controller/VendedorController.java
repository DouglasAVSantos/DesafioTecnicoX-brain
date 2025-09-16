package com.desafio.XBrain.vendedor.controller;

import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.dto.VendedorResponseDto;
import com.desafio.XBrain.vendedor.service.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vendedor")
public class VendedorController {

    private final VendedorService service;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos vendedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendedor criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Vendedor já cadastrado"),
    })
    public ResponseEntity<VendedorResponseDto> cadastrar(@RequestBody @Valid VendedorRequestDto request) {
        VendedorResponseDto novoVendedor = service.cadastrar(request);
        return ResponseEntity.created(URI.create("api/v1/vendedor/" + novoVendedor.id())).body(novoVendedor);
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint responsável por retornar um vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado."),
    })
    public ResponseEntity<VendedorResponseDto> getVendedor(@PathVariable Long id) {
        return ResponseEntity.ok(service.getVendedor(id));
    }

    @GetMapping("/")
    @Operation(description = "Endpoint responsável por listar todos vendedores")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200", description = "Lista de vendedores retornado com sucesso")
    )
    public ResponseEntity<List<VendedorResponseDto>> getLista() {
        return ResponseEntity.ok(service.getLista());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint responsável por deletar um vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vendedor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado."),
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por atualizar um vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vendedor não encontrado."),
    })
    public ResponseEntity<VendedorResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid VendedorRequestDto request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }
}
