package com.example.cloneslack.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class EnterRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "enter_room_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoom chatRoom;

    public EnterRoom(User user, ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
