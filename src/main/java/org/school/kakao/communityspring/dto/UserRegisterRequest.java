package org.school.kakao.communityspring.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserRegisterRequest(
        MultipartFile file,
        String email,
        String password,
        String nickname
) {
}
