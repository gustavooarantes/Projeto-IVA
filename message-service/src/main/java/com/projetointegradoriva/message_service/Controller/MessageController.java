package com.projetointegradoriva.message_service.Controller;

import com.projetointegradoriva.contract_service.MessageDTO;
import com.projetointegradoriva.message_service.Message.PrivateMessage;
import com.projetointegradoriva.message_service.Producer.MessageProducer;
import com.projetointegradoriva.message_service.Repository.PrivateMessageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final PrivateMessageRepository repository;
    private final MessageProducer producer;

    @Autowired
    public MessageController(PrivateMessageRepository repository, MessageProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // Envia mensagem para o RabbitMQ
    @PostMapping
    public MessageDTO sendMessage(@Valid @RequestBody MessageDTO messageDTO) {
        if (messageDTO.getTimestamp() == null) {
            messageDTO.setTimestamp(LocalDateTime.now());
        }
        producer.sendMessage(messageDTO);
        return messageDTO;
    }

    // Busca chat entre dois usuários
    @GetMapping("/chat/{user1}/{user2}")
    public List<MessageDTO> getChat(@PathVariable String user1, @PathVariable String user2) {
        List<PrivateMessage> messages = repository.findBySenderIdAndConsumerIdOrConsumerIdAndSenderId(
                user1, user2, user1, user2
        );
        return messages.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO toDTO(PrivateMessage pm) {
        return new MessageDTO(
                pm.getSenderId(),
                pm.getConsumerId(),
                pm.getContent(),
                pm.getTimestamp()
        );
    }
}
