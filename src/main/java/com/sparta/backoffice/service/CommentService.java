package com.sparta.backoffice.service;

import com.sparta.backoffice.dto.CommentRequestDto;
import com.sparta.backoffice.dto.CommentResponseDto;
import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.entity.Post;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.CommentRepository;
import com.sparta.backoffice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.backoffice.entity.UserRoleEnum.ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long postid, CommentRequestDto commentRequestDto, User user) {
        log.info("postid: " + postid + ", username: " + user.getUsername());

        // 댓글 생성
        Comment comment = new Comment(commentRequestDto);
        // 해당되는 post 찾기
        Post post = postRepository.findById(postid).orElseThrow();

        // 해당 comment에 post와 user 설정
        comment.setPost(post);
        comment.setUser(user);

        // repository에 save
        commentRepository.save(comment);

        // post의 댓글 리스트에 댓글 추가
        post.addcomment(comment);
        postRepository.save(post);

        // commentResponseDto에 담아서 return
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> updateComment(Long commentid, CommentRequestDto commentRequestDto, User user) {
        log.info("commentid: " + commentid + ", username: " + user.getUsername());

        // 해당되는 댓글 찾기
        Comment comment = findComment(commentid);

        if (comment.getUser().getUsername().equals(user.getUsername())|| user.getRole().equals(ADMIN) ) {
            // 댓글 내용 update
            comment.update(commentRequestDto);
            return ResponseEntity.ok().body("댓글 수정 완료");
        } else {
            return ResponseEntity.badRequest().body("댓글 수정 실패");
        }
    }

    public ResponseEntity<String> deleteComment(Long postid, Long commentid, User user) {
        log.info("commentid: " + commentid + ", username: " + user.getUsername());

        // 해당되는 댓글 찾기
        Comment comment = findComment(commentid);

        // 해당되는 post 찾기
        Post post = postRepository.findById(postid).orElseThrow();

        if (comment.getUser().getUsername().equals(user.getUsername()) || user.getRole().equals(ADMIN) ) {
            // 댓글 delete
            commentRepository.delete(comment);

            // 게시글의 댓글 리스트에서도 삭제하기
            post.deletecomment(comment);
            postRepository.save(post);

            return ResponseEntity.ok().body("댓글 삭제 완료");
        } else {
            return ResponseEntity.badRequest().body("댓글 삭제 실패");
        }

    }

    private Comment findComment(Long commentid) {
        return commentRepository.findById(commentid).orElseThrow();
    }

    public List<CommentResponseDto> getAllComment() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            commentResponseDtoList.add(new CommentResponseDto(commentList.get(i)));
        }
        return commentResponseDtoList;
    }
}
