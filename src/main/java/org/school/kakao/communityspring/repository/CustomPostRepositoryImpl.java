package org.school.kakao.communityspring.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.PostWithUserResponse;
import org.school.kakao.communityspring.model.QPost;

import java.util.List;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final EntityManager entityManager;

    @Override
    public List<PostWithUserResponse> findAllByOrderByCreatedAtDesc() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                        PostWithUserResponse.class, QPost.post, QPost.post.user
                ))
                .from(QPost.post)
                .orderBy(QPost.post.createdAt.desc()).fetch();
    }
}
