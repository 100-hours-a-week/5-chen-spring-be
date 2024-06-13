package org.school.kakao.communityspring.dto;

import lombok.Getter;
import org.school.kakao.communityspring.model.User;

@Getter
public class UserResponse {
    private final Long id;
    private final String profileImage;
    private final String email;
    private final String nickname;

    protected UserResponse(Long id, String profileImage, String email, String nickname) {
        this.id = id;
        this.profileImage = profileImage;
        this.email = email;
        this.nickname = nickname;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.profileImage = user.getProfileImage();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
