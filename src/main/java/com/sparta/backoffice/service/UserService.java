package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.AuthRequestDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.jwt.JwtUtil;
//import com.sparta.backoffice.repository.BlackListRepository;
import com.sparta.backoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();

        String nickname = requestDto.getNickname();

        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()) {
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        String password = passwordEncoder.encode(requestDto.getPassword());


        Optional<User> checkUsername = userRepository.findByUsername(username);

        if ((checkUsername.isPresent())) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }
        User user = new User(username, nickname, role, password);
        userRepository.save(user);
    }

    public void login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 아이디가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /* 미완성 */
//    @Transactional
//    public void logout(String token) {
//
//        // 토큰을 블랙리스트에 추가
//        BlackList blackList = new BlackList(token);
//        blackListRepository.save(blackList);
//    }

}
