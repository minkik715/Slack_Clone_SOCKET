package com.example.cloneslack.controller;


import com.example.cloneslack.dto.UserRequestDto;
import com.example.cloneslack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequestDto requestDto) {
        return userService.registerUser(requestDto);

    }

}
