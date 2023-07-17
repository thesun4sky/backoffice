package com.sparta.backoffice.controller;

import com.sparta.backoffice.dto.CommentRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private CommentService commentService;

    @PostMapping("/api/post/{postid}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {

    }

}
