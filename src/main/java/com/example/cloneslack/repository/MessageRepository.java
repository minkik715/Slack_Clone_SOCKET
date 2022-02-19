package com.example.cloneslack.repository;

import com.example.cloneslack.domain.ChatRoom;
import com.example.cloneslack.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoom(ChatRoom chatRoom);

}
