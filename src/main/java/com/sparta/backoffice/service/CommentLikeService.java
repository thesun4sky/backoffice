package com.sparta.backoffice.service;

import com.sparta.backoffice.entity.Comment;
import com.sparta.backoffice.entity.CommentLike;
import com.sparta.backoffice.entity.User;
import com.sparta.backoffice.repository.CommentLikeRepository;
import com.sparta.backoffice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<String> addcommentlike(Long commentid, User user) {
        // 댓글 찾기
        Comment comment = findComment(commentid);

        if (comment.getUser().getUsername().equals(user.getUsername())) {   // 댓글 작성자가 본인이라면
            return ResponseEntity.badRequest().body("본인의 댓글에는 좋아요를 누를 수 없습니다");
        } else {
            // 댓글 좋아요 기록 찾기
            Optional<CommentLike> commentLike = findCommentLike(comment, user);
            if (!commentLike.isPresent()) { // 기록이 없으면
                CommentLike newCommentLike = new CommentLike(user, comment);
                newCommentLike.setlike(true);
                commentLikeRepository.save(newCommentLike);

                // 댓글의 좋아요 수 + 1
                comment.setCommentlikeCount(comment.getCommentlikeCount() + 1);

                return ResponseEntity.ok().body("댓글 좋아요 성공");
            } else {                  // 이미 기록이 있으면
                if (commentLike.get().getLike()) { // 그리고 이미 true 라면
                    return ResponseEntity.badRequest().body("이미 댓글에 좋아요가 눌려 있습니다.");
                } else {                     // 기록이 false라면 true로 변경해줌
                    // 댓글 좋아요 기록 true로 변경
                    commentLike.get().setlike(true);

                    // 댓글의 좋아요 수 + 1
                    comment.setCommentlikeCount(comment.getCommentlikeCount() + 1);
                    return ResponseEntity.ok().body("댓글 좋아요 성공");
                }
            }
        }
    }

    @Transactional
    public ResponseEntity<String> cancelcommentlike(Long commentid, User user) {
        Comment comment = findComment(commentid);
        // 댓글 좋아요 기록 찾기
        Optional<CommentLike> commentLike = findCommentLike(comment, user);

        if (!commentLike.isPresent()) {       // 기록이 없다면
            return ResponseEntity.badRequest().body("댓글에 좋아요를 누른 적이 없습니다.");
        } else {                         // 기록이 있다면
            if (commentLike.get().getLike()) { // 그 기록이 true 라면
                // 댓글 좋아요 기록 false로 변경
                commentLike.get().setlike(false);

                // 댓글의 좋아요 수 + 1
                comment.setCommentlikeCount(comment.getCommentlikeCount() - 1);
                return ResponseEntity.ok().body("댓글 좋아요 취소 성공");
            } else {                     // 그 기록이 false 라면
                return ResponseEntity.badRequest().body("댓글에 이미 좋아요가 취소되어 있습니다.");
            }
        }
    }


    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    private Optional<CommentLike> findCommentLike(Comment comment, User user) {
        return commentLikeRepository.findByCommentAndUser(comment, user);
    }
}
