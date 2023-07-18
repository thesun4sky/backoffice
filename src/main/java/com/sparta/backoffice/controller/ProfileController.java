package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.ProfileRequestDto;
import com.sparta.backoffice.dto.ProfileResponseDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.entity.UserRoleEnum;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.ProfileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        //관리자 확인
        UserRoleEnum role = userDetails.getUser().getRole();


        //회원 정보 가져오기
        return profileService.getProfile(username, userDetails.getUser());




    }

    //회원 프로필 개별 조회(관리자 모드)
    @GetMapping("/profile/{id}")
    public ProfileResponseDto getProfileByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long id){
        //관리자 확인
        UserRoleEnum role = userDetails.getUser().getRole();

        return profileService.getProfileByAdmin(id,role);

    }


    //전체 프로필 조회(관리자 모드)
    @GetMapping("/profiles")
    public List<ProfileResponseDto> getProfileList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        //관리자 확인
        UserRoleEnum roleEnum = userDetails.getUser().getRole();
        //프로필 전체 조회
        return profileService.getProfileList(roleEnum);
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
    @PutMapping("profile/password/{id}")
    public ProfileResponseDto passwordUpdate(@RequestBody ProfileRequestDto profileRequestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable Long id) {


        //현재 비밀번호 확인
        String password = profileRequestDto.getPassword();


        //새 비밀번호 입력
        String newPassword = profileRequestDto.getNewPassword();

        return profileService.passwordUpdate(id, password, newPassword, userDetails.getUser());


    }


}



