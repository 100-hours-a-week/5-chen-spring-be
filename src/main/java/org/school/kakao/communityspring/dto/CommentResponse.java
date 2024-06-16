package org.school.kakao.communityspring.dto;

import org.school.kakao.communityspring.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime created_at
) {
    public CommentResponse(Comment comment) {
        this(comment.getId(), comment.getContent(), comment.getCreatedAt());
    }
}
