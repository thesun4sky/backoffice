package com.sparta.backoffice.dto;

import com.sparta.backoffice.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsResponseDto {

    private String title;
    private String nickname;
    private long comment_count;
    private long views;
    private long likeCount;

    public PostsResponseDto(Post post) {
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.comment_count = post.getCommentCount();
        this.views = post.getViews();
        this.likeCount = post.getLikeCount();
    }
}
