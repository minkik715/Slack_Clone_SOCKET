package com.example.cloneslack.controller;


import com.example.cloneslack.domain.MessageType;
import com.example.cloneslack.dto.MessageRequestDto;
import com.example.cloneslack.repository.ChatRoomRepository;
import com.example.cloneslack.repository.MessageRepository;
import com.example.cloneslack.security.UserDetailsImpl;
import com.example.cloneslack.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;


    //pub에서 전달된 데이터를 여기서 받음
    @MessageMapping("/chat/message")
    public void message(MessageRequestDto messageRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        // 받아온 정보는
        //{messagetype,
        // roomId,
        // message, }
        //토큰이 잘 전달되지 않으면 그냥 유저정보를 받으면 되긴함
        chatService.manageMessage(messageRequestDto,userDetails.getUser());
    }
}
