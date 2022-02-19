package com.example.cloneslack.controller;

import com.example.cloneslack.domain.ChatRoom;
import com.example.cloneslack.dto.CreateRoomDto;
import com.example.cloneslack.dto.EnterRoomResponseDto;
import com.example.cloneslack.dto.InvitationDto;
import com.example.cloneslack.security.UserDetailsImpl;
import com.example.cloneslack.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    // 각 유저에게 해당하는 room들의 정보 보내기
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatRoomService.getRooms(userDetails.getUser());
    }

    //룸 만들기
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDto createRoomDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatRoomService.createRoom(createRoomDto.getRoomName(),userDetails.getUser());
    }

    //하나의 룸에 해당하는 메세지 끌고오기
    @GetMapping("/room/enter/{roomId}")
    public EnterRoomResponseDto roomDetail(@PathVariable Long roomId){
         return chatRoomService.enterRoom(roomId);
    }

    //유저 초대하기
    @PostMapping("/room/enter/{roomId}")
    public ResponseEntity<?> invitationUser(@PathVariable Long roomId, @RequestBody InvitationDto invitationDto){
        //초대할 유저의 이메일이 필요함
        return chatRoomService.invitationUser(roomId, invitationDto.getUsername());
    }

}
