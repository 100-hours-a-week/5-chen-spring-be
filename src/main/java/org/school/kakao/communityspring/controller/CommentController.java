package org.school.kakao.communityspring.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.CommentResponse;
import org.school.kakao.communityspring.dto.CommentUpdateRequest;
import org.school.kakao.communityspring.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SecurityRequirement(name = "bearer-key")
@RequestMapping("/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id, @RequestBody CommentUpdateRequest request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Map.of("message", "OK");
    }
}
