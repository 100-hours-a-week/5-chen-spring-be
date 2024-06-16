package org.school.kakao.communityspring.controller;

import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.repository.CommentRepository;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/comments")
public class CommentController {
    private final CommentRepository commentRepository;
}
