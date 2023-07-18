package com.sparta.backoffice.controller;

import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.PostLikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostLikeController {

    private PostLikeService postLikeService;
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/api/post/{id}/like")
    public String addPostLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        postLikeService.addPostLike(id, user);
        return "좋습니다.";
    }

    @PutMapping("/api/post/{id}/like")
    public String cancelPostLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        postLikeService.cancelPostLike(id, user);
        return "안좋습니다.";
    }
}
