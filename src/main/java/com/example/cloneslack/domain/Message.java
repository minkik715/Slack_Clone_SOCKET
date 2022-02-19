package com.example.cloneslack.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Message extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="message_id")
    private Long id;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private MessageType messageType;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoom chatRoom;

    public Message(String content, MessageType messageType, User user, ChatRoom chatRoom) {
        this.content = content;
        this.messageType = messageType;
        this.user = user;
        this.chatRoom = chatRoom;
    }

}
