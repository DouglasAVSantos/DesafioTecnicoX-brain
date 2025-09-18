package com.desafio.XBrain.shared.handler;

import com.desafio.XBrain.shared.exception.ConflictException;
import com.desafio.XBrain.shared.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> conflictHandler(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFoundHandler(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Map<String, String>> dateTimeHandler(DateTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationHandler(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("erro", Arrays.stream(ex.getDetailMessageArguments()).filter(c -> !c.equals(new String(""))).iterator().next().toString()));
    }
}
