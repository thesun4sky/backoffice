package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.AuthRequestDto;
import com.sparta.backoffice.dto.StatusResponseDto;
import com.sparta.backoffice.jwt.JwtUtil;
import com.sparta.backoffice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/auth/signup")
    public ResponseEntity<StatusResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body((new StatusResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value())));
        }
        return ResponseEntity.status(201).body(new StatusResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<StatusResponseDto> login(@RequestBody AuthRequestDto requestDto, HttpServletResponse response) {
        try {
            userService.login(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusResponseDto("아이디 또는 비밀번호를 잘못 입력했습니다.", HttpStatus.BAD_REQUEST.value()));
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(requestDto.getUsername(), requestDto.isAdmin()));

        return ResponseEntity.ok().body(new StatusResponseDto("로그인 되었습니다.", HttpStatus.OK.value()));
    }

    /* 로그아웃 미완성 */
//    @PostMapping("/logout")
//    public ResponseEntity<StatusResponseDto> logout(HttpServletRequest request) {
//
//        // Access Token 검증
//        if (!jwtUtil.validateToken(jwtUtil.resolveToken(request))) {
//            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
//        }
//
//        String token = jwtUtil.resolveToken(request);
//
//        userService.logout(token);
//
//        return ResponseEntity.ok().body(new StatusResponseDto("로그아웃 되었습니다.", HttpStatus.OK.value()));
//    }
}
