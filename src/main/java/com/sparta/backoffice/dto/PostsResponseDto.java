package com.sparta.backoffice.dto;

import com.sparta.backoffice.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsResponseDto {

    private String title;
    private String nickname;
    private int comment_count;
    private long views;

    public PostsResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.comment_count = post.getCommentList().size();
        this.views = post.getViews();
    }
}
