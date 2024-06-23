package org.school.kakao.communityspring.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.CommentWithUserResponse;
import org.school.kakao.communityspring.dto.PostWithUserResponse;
import org.school.kakao.communityspring.service.CommentService;
import org.school.kakao.communityspring.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping
    public Stream<PostWithUserResponse> listAll() {
        return postService.listAll();
    }

    @GetMapping("/{id}")
    public PostWithUserResponse findById(@PathVariable(name = "id") Long id) {
        return postService.findById(id);
    }

    @GetMapping("/{id}/comments")
    public Stream<CommentWithUserResponse> findByPostId(@PathVariable(name = "id") Long id) {
        return commentService.findByPostId(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PostWithUserResponse create(
            @RequestPart("image") MultipartFile image,
            @RequestPart("content") String content,
            @RequestPart("title") String title,
            HttpServletResponse response
    ) {
        PostWithUserResponse result = postService.create(image, title, content);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return result;
    }
}
