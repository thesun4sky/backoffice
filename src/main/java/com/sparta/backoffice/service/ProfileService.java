package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.ProfileRequestDto;
import com.sparta.backoffice.dto.ProfileResponseDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //비밀번호 확인을 위한 queue 선언
    private final Queue<String> queue = new LinkedList<>();


    //프로필 정보 가져오기
    public ProfileResponseDto getProfile(String username, User user) {

        //회원 존재확인
        userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원은 존재하지 않습니다"));

        //회원 정보 보여주기
        return new ProfileResponseDto(user);


    }

    // 프로필 업데이트
    public ProfileResponseDto updateProfile(String password, String username, ProfileRequestDto profileRequestDto) {

        //회원 존재 확인
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다."));

        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //새 값 넣기

        user.setNickname(profileRequestDto.getNickname());

        // 저장
        user = userRepository.save(user);

        return new ProfileResponseDto(user);


    }

    public ProfileResponseDto passwordUpdate(String password, String newPassword, User user) {
        //회원 존재 확인
        userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지 않습니다."));

        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        }

        //user의 전비밀번호 받아오기
//        String lastPassword = user.getLastPassword();
//        String[] PassStr = lastPassword.split(" ");

        //전에 쓴 비밀번호인지 확인
//        for (int i = 0; i < PassStr.length; i++) {
//            if (passwordEncoder.matches(PassStr[i], newPassword)) {
//                throw new IllegalArgumentException("전에 쓰던 페스워드라서 못씀니다.");
//            }
//        }

//        if (PassStr.length == 1) {
//            user.lastPasswordUpdate(PassStr[1] + );
//        }
//        //배열에 저장
//        user.lastPasswordUpdate(String.join(" ", tempStr));

        
//        queue.add(password);

        //if문 사용해서 최근 3번안에 사용한 비밀번호인지 확인
//        for (int i = 0; i < 2; i++) {
//            if (queue.equals(newPassword)) {
//                System.out.println("이전에 사용한 비밀번호 입니다.");
//            } else {
//                queue.add(newPassword);
//                if (queue.size() == 3) { // queue사이즈가 꽉 차면
//                    queue.poll(); // 가장 먼저 들어온 값 버리기
//                }
//            }
//
//        }
        return new ProfileResponseDto(); // 추후에 값 바꿀 거임
    }
}
