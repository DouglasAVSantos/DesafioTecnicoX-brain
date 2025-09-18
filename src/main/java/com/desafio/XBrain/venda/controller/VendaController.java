package com.desafio.XBrain.venda.controller;

import com.desafio.XBrain.venda.dto.RelatorioDeVendasDto;
import com.desafio.XBrain.venda.dto.VendaRequestDto;
import com.desafio.XBrain.venda.dto.VendaResponseDto;
import com.desafio.XBrain.venda.service.VendaService;
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
    public ResponseEntity<VendaResponseDto> cadastrar(@RequestBody @Valid VendaRequestDto request) {
        VendaResponseDto response = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/api/v1/venda/" + response.id())).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<RelatorioDeVendasDto>> getRelatorio(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate inicio, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fim) {
        return ResponseEntity.ok(service.getRelatorio(inicio, fim));
    }
}
