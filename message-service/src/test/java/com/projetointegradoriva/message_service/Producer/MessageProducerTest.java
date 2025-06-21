package com.projetointegradoriva.message_service.Producer;

import com.projetointegradoriva.contract_service.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class MessageProducerTest {

    private RabbitTemplate rabbitTemplate;
    private MessageProducer messageProducer;

    @BeforeEach
    public void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        messageProducer = new MessageProducer(rabbitTemplate);

        // Simular injeção das configurações @Value manualmente (não são preenchidas automaticamente no teste unitário)
        // via reflection
        setField(messageProducer, "exchange", "test-exchange");
        setField(messageProducer, "routingKey", "test-routing-key");
    }

    @Test
    public void testSendMessage_CallsRabbitTemplateWithCorrectArguments() {
        // Arrange
        MessageDTO dto = new MessageDTO("user1", "user2", "Mensagem de teste", LocalDateTime.now());

        // Act
        messageProducer.sendMessage(dto);

        // Assert
        verify(rabbitTemplate, times(1))
                .convertAndSend("test-exchange", "test-routing-key", dto);
    }

    // Utilitário para injetar campos privados com @Value simulando comportamento do Spring
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
