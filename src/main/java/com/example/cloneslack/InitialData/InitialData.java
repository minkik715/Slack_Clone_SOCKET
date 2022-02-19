package com.example.cloneslack.InitialData;

import com.example.cloneslack.domain.User;
import com.example.cloneslack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitialData implements ApplicationRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        String password = bCryptPasswordEncoder.encode("123456");
        User user = new User("123@gmail.com", "익명의 프로그래머",password );

        userRepository.save(user);
    }
}
