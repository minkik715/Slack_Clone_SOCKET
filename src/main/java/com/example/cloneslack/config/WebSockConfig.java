package com.example.cloneslack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {


    //서버와 처음 연결해주는 부분
    //WebSocket Open!
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("http://localhost:8080")
                .withSockJS();
    }

    //메세지 송수신을 처리하는 부분
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //sub 로 보내면 이곳을 한번 거쳐서 프론트에 데이터를 전달해준다.
        registry.enableSimpleBroker("/sub");
        //pub 로 데이터를 받으면 이곳을 한번 거쳐서 URI 만 MessageMapping 에 매핑이 된다.
        //ex pub/chat/message 라면 pub 를 제외하고 /chat/message 를 @MessageMapping 에 매핑한다.
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
