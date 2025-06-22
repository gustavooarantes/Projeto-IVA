package com.projetointegradoriva.message_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetointegradoriva.contract_service.MessageDTO;
import com.projetointegradoriva.message_service.Message.PrivateMessage;
import com.projetointegradoriva.message_service.Producer.MessageProducer;
import com.projetointegradoriva.message_service.Repository.PrivateMessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrivateMessageRepository repository;

    @MockBean
    private MessageProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSendMessage() throws Exception {
        MessageDTO dto = new MessageDTO("user1", "user2", "Olá!", null);

        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.senderId").value("user1"))
                .andExpect(jsonPath("$.consumerId").value("user2"))
                .andExpect(jsonPath("$.content").value("Olá!"));

        Mockito.verify(producer).sendMessage(any(MessageDTO.class));
    }

    @Test
    public void testGetChat() throws Exception {
        PrivateMessage msg1 = new PrivateMessage("user1", "user2", "Oi!", LocalDateTime.now());
        PrivateMessage msg2 = new PrivateMessage("user2", "user1", "Olá!", LocalDateTime.now());
        List<PrivateMessage> messages = Arrays.asList(msg1, msg2);

        Mockito.when(repository.findBySenderIdAndConsumerIdOrConsumerIdAndSenderId(
                "user1", "user2", "user1", "user2")).thenReturn(messages);

        mockMvc.perform(get("/api/messages/chat/user1/user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].senderId").value("user1"))
                .andExpect(jsonPath("$[1].senderId").value("user2"));
    }
}
