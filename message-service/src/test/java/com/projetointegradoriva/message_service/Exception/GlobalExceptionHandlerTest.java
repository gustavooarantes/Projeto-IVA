package com.projetointegradoriva.message_service.Exception;

import com.projetointegradoriva.contract_service.MessageDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationExceptions_ReturnsBadRequestWithErrors() {
        // Simula um DTO inválido
        MessageDTO dto = new MessageDTO(); // campos nulos ou vazios

        // Simula erros de validação manualmente
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(dto, "messageDTO");
        bindingResult.addError(new FieldError("messageDTO", "senderId", "SenderId é obrigatório"));
        bindingResult.addError(new FieldError("messageDTO", "consumerId", "ConsumerId é obrigatório"));
        bindingResult.addError(new FieldError("messageDTO", "content", "Content não pode estar vazio"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Chama o método do handler
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Verificações
        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("senderId"));
        assertTrue(response.getBody().containsKey("consumerId"));
        assertTrue(response.getBody().containsKey("content"));
        assertEquals("SenderId é obrigatório", response.getBody().get("senderId"));
    }
}
