package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.PostRequestDto;
import com.sparta.backoffice.dto.PostResponseDto;
import com.sparta.backoffice.dto.PostsResponseDto;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    // repository 주입받음
    PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 생성
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
        post.setNickname(setAnonymous(requestDto, user));
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //게시글 전체조회: 제목, 닉네임, 댓글수, 조회수만 List형태로 만들어 return 한다.
    public List<PostsResponseDto> getAllPost() {
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        for (Post post: postList) {
            postsResponseDtoList.add(new PostsResponseDto(post));
        }

        return postsResponseDtoList;
    }


    //게시글 하나조회: PostResponseDto를 반환함과동시에 Transactional환경을 걸어서 views(조회수)를 하나올린다.
    @Transactional
    public PostResponseDto getOnePost(Long id) {
        // post entity를 찾아옴
        Post post = findById(id);
        // views를 하나 올린뒤 -> Dto로 만들어서 return
        post.plusViews(post);
        return new PostResponseDto(post);
    }

    //게시글 업데이트
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {
        // post entity를 찾아옴
        Post post = findById(id);

        if (post.getUsername().equals(user.getUsername())) {
            post.update(requestDto);
            post.setNickname(setAnonymous(requestDto, user));
        } else {
            throw new IllegalArgumentException("본인이 아니면 삭제할수 없습니다.");
        }

        return new PostResponseDto(post);
    }

    //게시글 삭제
    public void deletePost(Long id, User user) {
        Post post = findById(id);
        if (post.getUsername().equals(user.getUsername())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("본인이 아니면 삭제할수 없습니다.");
        }
    }


    // id로 Post entity를 찾아주는 메서드
    private Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }
    // request의 anonymous가 true일 경우 익명 닉에임으로, 아니면 그냥 닉네임을 반환하는 메서드
    private String setAnonymous(PostRequestDto requestDto, User user) {
        if (requestDto.isAnonymous()) {   //만약에 익명이 true로 되어있다면 앞에 한자리를 제외하고 *로 처리함
            return user.getNickname().charAt(0) + "*".repeat(user.getNickname().length() - 1);
        } else {
            return user.getNickname();
        }
    }
}
