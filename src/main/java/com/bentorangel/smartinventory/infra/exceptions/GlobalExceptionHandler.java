package com.bentorangel.smartinventory.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Tratamento para erros de Validação (@Valid do DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();

        // Aproveitando para mapear os campos com erro
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        StandardErrorDTO errorResponse = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos dados enviados.",
                request.getRequestURI(),
                fieldErrors // Aqui injetamos o seu Map de erros!
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 2. Tratamento para Regras de Negócio e "Não Encontrado"
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorDTO> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

        boolean isNotFound = ex.getMessage() != null && ex.getMessage().toLowerCase().contains("encontrado");
        HttpStatus status = isNotFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        StandardErrorDTO errorResponse = new StandardErrorDTO(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                request.getRequestURI(),
                null // Não há campos específicos com erro aqui
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    // 3. O DTO (Record) interno que define o "Contrato" do nosso erro
    public record StandardErrorDTO(
            LocalDateTime timestamp,
            Integer status,
            String message,
            String path,
            Map<String, String> fieldErrors // Opcional: só aparece se tiver erro de validação
    ) {}

    // 4. Tratamento para erros inesperados (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleGenericException(Exception ex, HttpServletRequest request) {

        StandardErrorDTO errorResponse = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno inesperado no servidor. Tente novamente mais tarde.",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}