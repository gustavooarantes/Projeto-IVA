package com.projetointegradoriva.message_service.Repository;

import com.projetointegradoriva.message_service.Message.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<PrivateMessage> findBySenderIdAndConsumerIdOrConsumerIdAndSenderId(
            String senderId1, String consumerId1, String consumerId2, String senderId2
    );
}
