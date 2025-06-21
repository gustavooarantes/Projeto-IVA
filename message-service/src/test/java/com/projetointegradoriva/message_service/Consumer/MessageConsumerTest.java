package com.projetointegradoriva.message_service.Consumer;

import com.projetointegradoriva.contract_service.MessageDTO;
import com.projetointegradoriva.message_service.Message.PrivateMessage;
import com.projetointegradoriva.message_service.Repository.PrivateMessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageConsumerTest {

    @Test
    public void testReceiveMessageShouldSaveEntity() {
        // Arrange
        PrivateMessageRepository mockRepository = mock(PrivateMessageRepository.class);
        MessageConsumer consumer = new MessageConsumer(mockRepository);

        MessageDTO dto = new MessageDTO();
        dto.setSenderId("user1");
        dto.setConsumerId("user2");
        dto.setContent("Olá do RabbitMQ!");
        dto.setTimestamp(LocalDateTime.now());

        // Act
        consumer.receiveMessage(dto);

        // Assert
        ArgumentCaptor<PrivateMessage> captor = ArgumentCaptor.forClass(PrivateMessage.class);
        verify(mockRepository, times(1)).save(captor.capture());

        PrivateMessage saved = captor.getValue();
        assertEquals("user1", saved.getSenderId());
        assertEquals("user2", saved.getConsumerId());
        assertEquals("Olá do RabbitMQ!", saved.getContent());
        assertEquals(dto.getTimestamp(), saved.getTimestamp());
    }
}
