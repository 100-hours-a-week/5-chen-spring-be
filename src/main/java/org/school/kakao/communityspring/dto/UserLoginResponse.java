package org.school.kakao.communityspring.dto;

public record UserLoginResponse(
        String token,
        UserResponse userResponse
) {
}
