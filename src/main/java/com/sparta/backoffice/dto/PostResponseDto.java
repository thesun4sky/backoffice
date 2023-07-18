package com.sparta.backoffice.dto;

import com.sparta.backoffice.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private String username;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private long views;

    public PostResponseDto(Post post) {
        this.username = post.getUsername();
        this.nickname = post.getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.views = post.getViews();
    }
}
