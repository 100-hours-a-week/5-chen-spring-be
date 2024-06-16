package org.school.kakao.communityspring.repository;

import org.school.kakao.communityspring.model.Comment;
import org.school.kakao.communityspring.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = "user")
    List<Comment> findByPostOrderByCreatedAtDesc(Post post);
}
