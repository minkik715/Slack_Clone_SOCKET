package com.example.cloneslack.service;

import com.example.cloneslack.domain.ChatRoom;
import com.example.cloneslack.domain.EnterRoom;
import com.example.cloneslack.domain.Message;
import com.example.cloneslack.domain.User;
import com.example.cloneslack.dto.EnterRoomResponseDto;
import com.example.cloneslack.repository.ChatRoomRepository;
import com.example.cloneslack.repository.EnterRepository;
import com.example.cloneslack.repository.MessageRepository;
import com.example.cloneslack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EnterRepository enterRepository;

    private final MessageRepository messageRepository;

    @Transactional
    public ResponseEntity<?> createRoom(String name, User user) {
        //유저 찾기
        Optional<User> tmp = userRepository.findById(user.getId());
        User findUser = tmp.get();

        //채팅방 만들기
        ChatRoom chatRoom = new ChatRoom(name);
        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);

        //채팅방과 유저의 연관관계 맺어주기
        EnterRoom enterRoom = new EnterRoom(findUser, saveChatRoom);
        enterRepository.save(enterRoom);
        return ResponseEntity.ok("채널 생성 성공!");
    }

    public List<ChatRoom> getRooms(User user) {
        // 들어온 유저가 해당하는 모든 방들 다꺼내기
        List<EnterRoom> enterRooms = enterRepository.findByUserOrderByIdDesc(user);
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (EnterRoom enterRoom : enterRooms) {
            chatRooms.add(enterRoom.getChatRoom());
        }
        return chatRooms;
    }

    public EnterRoomResponseDto enterRoom(Long roomId) {
        //먼저 룸 찾기
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("그런 방은 존재하지 않습니다.")
        );
        String chatName = chatRoom.getName();

        //룸에 해당하는 메세지 전부 찾기
        List<Message> messageInRoom = messageRepository.findByChatRoom(chatRoom);
        EnterRoomResponseDto enterRoomResponseDto = new EnterRoomResponseDto();
        //dto에 방 이름과 방에 들어있는 메세지들 전부 보내기
        enterRoomResponseDto.setName(chatName);
        enterRoomResponseDto.setMessageList(messageInRoom);
        return enterRoomResponseDto;
    }

    @Transactional
    public ResponseEntity<?> invitationUser(Long roomId, String username) {

        //이메일으로 초대 될 유저 찾기
        Optional<User> tmp = userRepository.findByUsername(username);
        if(!tmp.isPresent()){
            return ResponseEntity.badRequest().body(new IllegalArgumentException("해당 이메일은 존재하지 않습니다."));
        }
        //룸 번호로 룸 찾기
        User invitationUser = tmp.get();
        Optional<ChatRoom> tmp2 = chatRoomRepository.findById(roomId);
        if(!tmp2.isPresent()){
            return ResponseEntity.badRequest().body(new IllegalArgumentException("해당 채널은 존재하지 않습니다."));
        }
        ChatRoom chatRoom = tmp2.get();

        //룸과 유저 연관관계 맺기
        EnterRoom enterRoom = new EnterRoom();
        enterRoom.setUser(invitationUser);
        enterRoom.setChatRoom(chatRoom);
        enterRepository.save(enterRoom);
        return ResponseEntity.ok("초대 성공!");
    }
}
