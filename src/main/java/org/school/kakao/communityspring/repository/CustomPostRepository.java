package org.school.kakao.communityspring.repository;

import org.school.kakao.communityspring.dto.PostWithUserResponse;

import java.util.List;

public interface CustomPostRepository {
    List<PostWithUserResponse> findAllByOrderByCreatedAtDesc();
}
