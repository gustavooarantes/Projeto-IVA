package com.projetointegradoriva.message_service.Message;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateMessageTest {

    @Test
    public void testGettersAndSetters() {
        PrivateMessage pm = new PrivateMessage();

        Long id = 1L;
        String senderId = "user1";
        String consumerId = "user2";
        String content = "Mensagem de teste";
        LocalDateTime timestamp = LocalDateTime.now();

        pm.setId(id);
        pm.setSenderId(senderId);
        pm.setConsumerId(consumerId);
        pm.setContent(content);
        pm.setTimestamp(timestamp);

        assertEquals(id, pm.getId());
        assertEquals(senderId, pm.getSenderId());
        assertEquals(consumerId, pm.getConsumerId());
        assertEquals(content, pm.getContent());
        assertEquals(timestamp, pm.getTimestamp());
    }

    @Test
    public void testDefaultConstructor() {
        PrivateMessage pm = new PrivateMessage();
        assertNotNull(pm);
    }
}
