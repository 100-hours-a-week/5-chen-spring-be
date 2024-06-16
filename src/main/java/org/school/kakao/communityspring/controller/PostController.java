package org.school.kakao.communityspring.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.CommentWithUserResponse;
import org.school.kakao.communityspring.dto.PostWithUserResponse;
import org.school.kakao.communityspring.model.Comment;
import org.school.kakao.communityspring.model.Post;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.CommentRepository;
import org.school.kakao.communityspring.repository.PostRepository;
import org.school.kakao.communityspring.service.AuthContextUtil;
import org.school.kakao.communityspring.service.ImageStorage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final ImageStorage imageStorage;
    private final AuthContextUtil authContextUtil;
    private final CommentRepository commentRepository;

    @GetMapping
    public Stream<PostWithUserResponse> listAll() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(post -> new PostWithUserResponse(post, post.getUser()));
    }

    @GetMapping("/{id}")
    public PostWithUserResponse findById(@PathVariable(name = "id") Long id) {
        return postRepository.findById(id).map(post -> new PostWithUserResponse(post, post.getUser()))
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    @GetMapping("/{id}/comments")
    public Stream<CommentWithUserResponse> findByPostId(@PathVariable(name = "id") Long id) {
        Post postRef = postRepository.getReferenceById(id);
        List<Comment> posts = commentRepository.findByPostOrderByCreatedAtDesc(postRef);
        return posts.stream().map(CommentWithUserResponse::new);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostWithUserResponse create(
            @RequestPart("file") MultipartFile file,
            @RequestPart("content") String content,
            @RequestPart("title") String title
    ) {
        String image = imageStorage.save(file);
        User user = authContextUtil.loginUser();
        Post post = new Post(image, title, content, user);
        postRepository.save(post);
        return new PostWithUserResponse(post, user);
    }
}
