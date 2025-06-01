package com.projetointegradoriva.contract_service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MessageDTO {
    @NotBlank
    private String senderId;
    @NotBlank
    private String consumerId;
    @NotBlank
    private String content;
    @NotNull
    private LocalDateTime timestamp;

    public MessageDTO() {}

    public MessageDTO(String senderId, String consumerId, String content, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.consumerId = consumerId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getConsumerId() { return consumerId; }
    public void setConsumerId(String consumerId) { this.consumerId = consumerId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}