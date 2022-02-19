package com.example.cloneslack.dto;

import com.example.cloneslack.domain.MessageType;
import lombok.Data;

@Data
public class MessageRequestDto {
    private MessageType messageType;
    private Long roomId;
    private String message;

}
