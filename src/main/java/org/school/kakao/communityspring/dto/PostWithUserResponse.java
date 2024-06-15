package org.school.kakao.communityspring.dto;

import org.school.kakao.communityspring.model.Post;
import org.school.kakao.communityspring.model.User;

import java.time.LocalDateTime;
import java.util.Map;

public record PostWithUserResponse(
        Long id,
        String image,
        String title,
        String content,
        Integer view_count,
        LocalDateTime created_at,
        Map<String, String> author
) {
    public PostWithUserResponse(Post post, User user) {
        this(post.getId(), post.getImage(), post.getTitle(), post.getContent(), post.getViewCount(), post.getCreatedAt(),
                Map.of("nickname", user.getNickname(),
                        "profile_image", user.getProfileImage()));
    }
}
