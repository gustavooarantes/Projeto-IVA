package com.projetointegradoriva.message_service.Consumer;

import com.projetointegradoriva.contract_service.MessageDTO;
import com.projetointegradoriva.message_service.Message.PrivateMessage;
import com.projetointegradoriva.message_service.Repository.PrivateMessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final PrivateMessageRepository repository;

    @Autowired
    public MessageConsumer(PrivateMessageRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(MessageDTO dto) {
        PrivateMessage entity = new PrivateMessage();
        entity.setSenderId(dto.getSenderId());
        entity.setConsumerId(dto.getConsumerId());
        entity.setContent(dto.getContent());
        entity.setTimestamp(dto.getTimestamp());
        repository.save(entity);
    }
}
