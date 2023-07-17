package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.AuthRequestDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        UserRoleEnum role = requestDto.getRole();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);

        if ((checkUsername.isPresent())) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }
        User user = new User(username, nickname, role, password);
        userRepository.save(user);
    }





}
