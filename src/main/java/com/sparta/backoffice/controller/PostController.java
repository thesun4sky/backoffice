package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostsResponseDto;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.createPost(requestDto, user);
    }

    // 게시글 전체 조회
    @GetMapping("/posts/{method}")
    public List<PostsResponseDto> getAllPost(@PathVariable String method) {
        return postService.getAllPost(method);
    }

    // 게시글 하나 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getOnePost(@PathVariable Long id) {
        return postService.getOnePost(id);
    }

    // 게시글 업데이트
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return postService.updatePost(id, requestDto, user);
    }

    //게시글 삭제
    @DeleteMapping("/post/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        postService.deletePost(id, user);
    }

    //게시글 검색
    @GetMapping("/post")
    public List<PostsResponseDto> searchPost(@RequestParam String text) {
        return postService.searchPost(text);
    }
}
