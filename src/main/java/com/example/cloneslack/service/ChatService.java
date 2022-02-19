package com.example.cloneslack.service;

import com.example.cloneslack.domain.ChatRoom;
import com.example.cloneslack.domain.Message;
import com.example.cloneslack.domain.MessageType;
import com.example.cloneslack.domain.User;
import com.example.cloneslack.dto.MessageRequestDto;
import com.example.cloneslack.repository.ChatRoomRepository;
import com.example.cloneslack.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessageSendingOperations messageTemplate;


    @Transactional
    public void manageMessage(MessageRequestDto messageRequestDto, User user) {
        //받아온 정보중 roomId를 통해 먼저 room을 찾기
        ChatRoom findRoom = chatRoomRepository.findById(messageRequestDto.getRoomId()).orElseThrow(
                () -> new IllegalArgumentException("그런 방은 존재하지 않습니다.")
        );
        //메시지 만들기
        Message message = new Message(messageRequestDto.getMessage(),messageRequestDto.getMessageType(),user,findRoom);

        //만약 메시지의 타입이 ENTER 라면 처음 입장하는 것 입장했다고 sub에 메세지 보내주고
        if(MessageType.ENTER.equals(messageRequestDto.getMessageType())){
            message.setContent(user.getNickname()+"님이 입장하셨습니다.");
        }
        messageRepository.save(message);
        // /sub/chat/room/roomId 로 받아온 메세지 보내기
        // webconfig 에서 sub를 타고 front쪽으로 나감
        messageTemplate.convertAndSend("/sub/chat/room"+messageRequestDto.getRoomId(), message);

    }
}
