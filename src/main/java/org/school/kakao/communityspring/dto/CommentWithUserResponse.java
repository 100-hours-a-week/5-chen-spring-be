package org.school.kakao.communityspring.dto;

import org.school.kakao.communityspring.model.Comment;

import java.time.LocalDateTime;
import java.util.Map;

public record CommentWithUserResponse(
        String content,
        LocalDateTime created_at,
        Map<String, String> author
) {
    public CommentWithUserResponse(Comment comment) {
        this(comment.getContent(), comment.getCreatedAt(), Map.of(
                "nickname", comment.getUser().getNickname(),
                "profile_image", comment.getUser().getProfileImage()
        ));
    }
}
