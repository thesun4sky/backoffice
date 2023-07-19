package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.CommentLikeService;
import com.sparta.backoffice.service.PostLikeService;
import com.sparta.backoffice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ViewController {

    private final PostService postService;
    private final CommentLikeService commentLikeService;
    private final PostLikeService postlikeService;

    @GetMapping("/post/{id}")
    public String getOnePost(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 게시글 데이터
        PostResponseDto postResponseDto = postService.getOnePost(id);
        model.addAttribute("post", postResponseDto);

        // 게시글 좋아요 데이터
        String like = postlikeService.likefind(id, userDetails.getUser());
        log.info(like);
        model.addAttribute("like", like);

        List<CommentResponseDto> commentResponseDtoList = commentLikeService.commentlikefind(id, userDetails.getUser(), postResponseDto.getCommentResponseDtoList());
        // 게시글에 해당하는 댓글 데이터
        model.addAttribute("comments", commentResponseDtoList);

        // post.html 뷰 조회
        return "post";
    }

}

