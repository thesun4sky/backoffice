package com.sparta.backoffice.entity;

import com.sparta.backoffice.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String nickname;
    @Column
    private long views;
    @Column
    private long likeCount;
    @Column
    private long commentCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user) {
        this.username = user.getUsername();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.views = 0;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public void plusViews(Post post) {
        post.views += 1;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void addcomment(Comment comment) {
        this.commentList.add(comment);
        this.commentCount += 1;
    }

    public void deletecomment(Comment comment) {
        this.commentList.remove(comment);
        this.commentCount -= 1;
    }

}
