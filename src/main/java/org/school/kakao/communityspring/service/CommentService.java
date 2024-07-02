package org.school.kakao.communityspring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.CommentCreateRequest;
import org.school.kakao.communityspring.dto.CommentResponse;
import org.school.kakao.communityspring.dto.CommentUpdateRequest;
import org.school.kakao.communityspring.dto.CommentWithUserResponse;
import org.school.kakao.communityspring.model.Comment;
import org.school.kakao.communityspring.model.Post;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.CommentRepository;
import org.school.kakao.communityspring.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthContextUtil authContextUtil;

    public Stream<CommentWithUserResponse> findByPostId(Long postId) {
        Post postRef = postRepository.getReferenceById(postId);
        List<Comment> posts = commentRepository.findByPostOrderByCreatedAtDesc(postRef);
        return posts.stream().map(CommentWithUserResponse::new);
    }

    public CommentResponse update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment Not found"));
        comment.update(request.content());
        Comment saved = commentRepository.save(comment);
        return new CommentResponse(saved);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public CommentResponse create(Long postId, CommentCreateRequest createRequest) {
        Post post = postRepository.getReferenceById(postId);
        User user = authContextUtil.loginUser();
        Comment saved = commentRepository.save(new Comment(createRequest.content(), user, post));
        return new CommentResponse(saved);
    }
}
