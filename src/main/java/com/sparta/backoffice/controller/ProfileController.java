package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.ProfileRequestDto;
import com.sparta.backoffice.dto.ProfileResponseDto;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.ProfileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Queue;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;


    //프로필 정보 가져오기
    @GetMapping("/profile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        //회원 이름 받기
        String username = userDetails.getUser().getUsername();

        //회원 정보 가져오기
        return profileService.getProfile(username, userDetails.getUser());


    }

    //프로필 수정
    @PutMapping("/profile/update")
    public ProfileResponseDto profileUpdate(@RequestBody ProfileRequestDto profileRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                            HttpServletResponse servletResponse) {

        //회원 이름
        String username = profileRequestDto.getUsername();

        //회원 비밀번호 받기
        String password = profileRequestDto.getPassword();
        //회원 정보 수정
        return profileService.updateProfile(password, username, profileRequestDto);


    }

    //프로필 삭제
    @PutMapping("profile/password")
    public ProfileResponseDto passwordUpdate(@RequestBody ProfileRequestDto profileRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {


        //현재 비밀번호 확인
        String password = profileRequestDto.getPassword();


        //새 비밀번호 입력
        String newPassword = profileRequestDto.getNewPassword();

        return profileService.passwordUpdate(password, newPassword, userDetails.getUser());


    }


}



