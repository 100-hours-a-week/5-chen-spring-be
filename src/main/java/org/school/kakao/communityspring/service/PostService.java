package org.school.kakao.communityspring.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.PostWithUserResponse;
import org.school.kakao.communityspring.model.Post;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ImageStorage imageStorage;
    private final AuthContextUtil authContextUtil;

    public List<PostWithUserResponse> listAll() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public PostWithUserResponse findById(Long id) throws EntityNotFoundException {
        return postRepository.findById(id).map(post -> new PostWithUserResponse(post, post.getUser()))
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public PostWithUserResponse create(MultipartFile imageFile, String title, String content) {
        String image = imageStorage.store(imageFile);
        User user = authContextUtil.loginUser();
        Post post = new Post(image, title, content, user);
        postRepository.save(post);
        return new PostWithUserResponse(post, user);
    }
}
