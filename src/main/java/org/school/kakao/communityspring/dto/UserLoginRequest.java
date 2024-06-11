package org.school.kakao.communityspring.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}
