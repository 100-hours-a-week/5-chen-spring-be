package org.school.kakao.communityspring.controller;

import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
