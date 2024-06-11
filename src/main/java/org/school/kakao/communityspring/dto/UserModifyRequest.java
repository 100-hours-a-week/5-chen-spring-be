package org.school.kakao.communityspring.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserModifyRequest(
        MultipartFile image,
        String nickname
) {
}
