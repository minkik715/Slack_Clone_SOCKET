package com.example.cloneslack.dto;

import com.example.cloneslack.domain.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EnterRoomResponseDto {
    private String name;
    List<Message> messageList = new ArrayList<>();
}
