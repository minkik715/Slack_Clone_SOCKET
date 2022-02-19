package com.example.cloneslack.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    public ChatRoom(String name){
        this.name = name;
    }
}
