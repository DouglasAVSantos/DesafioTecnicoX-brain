package com.desafio.XBrain.shared.handler;

import com.desafio.XBrain.shared.exception.ConflictException;
import com.desafio.XBrain.shared.exception.NotFoundException;
import com.desafio.XBrain.vendedor.controller.VendedorController;
import com.desafio.XBrain.vendedor.dto.VendedorRequestDto;
import com.desafio.XBrain.vendedor.service.VendedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VendedorController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService service;
    private VendedorRequestDto request;

    @BeforeEach
    public void setUp() {
        request = new VendedorRequestDto("Douglas", "Santos");
    }

    @Test
    void deveRetornar409QuandoVendedorJaExiste() throws Exception {

        when(service.cadastrar(any())).thenThrow(
                new ConflictException("Vendedor '" + request.nomeCompleto() + "' já cadastrado")
        );

        mockMvc.perform(post("/api/v1/vendedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {"nome":"Douglas","sobrenome":"Santos"}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.erro")
                        .value("Vendedor 'douglas santos' já cadastrado"));
    }

    @Test
    void deveRetornar400QuandoRequestInvalido() throws Exception {

        String requestInvalido = new String("""
                {
                "nome":"",
                "sobrenome":"Santos"
                }
                """);
        mockMvc.perform(post("/api/v1/vendedor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro")
                        .value("nome: Campo obrigatório"));
    }

    @Test
    void deveRetornar404QuandoVendedorNaoEncontrado() throws Exception {
        when(service.getVendedor(1L)).thenThrow(new NotFoundException("Vendedor não encontrado para o id: 1"));

        mockMvc.perform(get("/api/v1/vendedor/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro")
                        .value("Vendedor não encontrado para o id: 1"));
    }
}