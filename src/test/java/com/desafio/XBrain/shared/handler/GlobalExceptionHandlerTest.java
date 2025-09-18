package com.desafio.XBrain.shared.handler;

import com.desafio.XBrain.shared.exception.ConflictException;
import com.desafio.XBrain.shared.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.DateTimeException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Test
    void deveTratarConflictException() {
        ConflictException ex = new ConflictException("Erro de conflito");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.conflictHandler(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Erro de conflito", response.getBody().get("erro"));
    }

    @Test
    void deveTratarNotFoundException() {
        NotFoundException ex = new NotFoundException("Recurso não encontrado");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.notFoundHandler(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Recurso não encontrado", response.getBody().get("erro"));
    }

    @Test
    void deveTratarDateTimeException() {
        DateTimeException ex = new DateTimeException("Erro de data e hora");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.dateTimeHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro de data e hora", response.getBody().get("erro"));
    }

    @Test
    void deveTratarMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getDetailMessageArguments())
                .thenReturn(new Object[]{"Registro nao encontrado"});
        ResponseEntity<Map<String, String>> result = globalExceptionHandler.validationHandler(ex);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(result.getBody().values().iterator().next(), "[Registro nao encontrado]");
        assertEquals(result.getBody().keySet().iterator().next(), "erro");
    }
}
