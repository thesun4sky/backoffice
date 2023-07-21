package com.sparta.backoffice.controller;

import com.sparta.backoffice.security.UserDetailsImpl;
import com.sparta.backoffice.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/api/comment/{commentid}/like")
    public ResponseEntity<String> addcommentlike(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.addcommentlike(commentid, userDetails.getUser());
    }

    @PutMapping("/api/comment/{commentid}/like")
    public ResponseEntity<String> cancelcommentlike(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.cancelcommentlike(commentid, userDetails.getUser());
    }
}
