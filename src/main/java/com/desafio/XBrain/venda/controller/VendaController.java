package com.desafio.XBrain.venda.controller;

import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/venda")
public class VendaController {

    private final VendaService service;

    @PostMapping
    @Operation(
            summary = "Criar Venda",
            description = "Endpoint responsável por criar uma venda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Json inválido"),
    })
    public ResponseEntity<VendaResponseDto> cadastra(@RequestBody @Valid VendaRequestDto request) {
        VendaResponseDto response = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/api/v1/venda/" + response.id())).body(response);
    }

    @GetMapping("/relatorio/")
    @Operation(
            summary = "Relatório de vendas por vendedor",
            description = "Endpoint responsável por retornar o relatório de vendas por vendedor com a media de vendas por dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "A data inicial informada é superior a data final informada"),
    })
    public ResponseEntity<List<RelatorioDeVendasDto>> getRelatorio(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate inicio, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fim) {
        return ResponseEntity.ok(service.getRelatorio(inicio, fim));
    }

    @GetMapping("/")
    @Operation(
            summary = "Listar vendas",
            description = "Endpoint responsável por retornar a todas as vendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendas retornadas com sucesso")
    })
    public ResponseEntity<List<VendaResponseDto>> getLista() {
        return ResponseEntity.ok(service.getLista());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca venda por ID",
            description = "Endpoint responsável por retornar uma venda especifica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada para o ID informado")
    })
    public ResponseEntity<VendaResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Cancelar venda por ID",
            description = "Endpoint responsável por cancelar uma venda especifica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada para o ID informado")
    })
    public ResponseEntity<Void> deleta(@PathVariable Long id) {
        service.cencela(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar venda por ID",
            description = "Endpoint responsável por atualizar uma venda especifica existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Venda ou Vendedor não encontrada(o) para o ID informado")
    })
    public ResponseEntity<VendaResponseDto> atualiza(@PathVariable Long id, @RequestBody @Valid VendaRequestDto request) {
        return ResponseEntity.ok(service.atualiza(id, request));
    }
}

