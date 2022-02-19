package com.example.cloneslack.service;

import com.example.cloneslack.domain.User;
import com.example.cloneslack.dto.UserRequestDto;
import com.example.cloneslack.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Setter
@Getter
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity registerUser(UserRequestDto requestDto) {

        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            return ResponseEntity.badRequest().body(new IllegalArgumentException("중복된 사용자 ID 가 존재합니다."));
        }
        if(!requestDto.getPassword().equals(requestDto.getPasswordcheck())){
            return ResponseEntity.badRequest().body(new IllegalArgumentException("비밀번호가 일치하지 않습니다."));
        }
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto);
        user.setPassword(password);
        userRepository.save(user);
        return ResponseEntity.ok("저장성공");
    }
}