package org.school.kakao.communityspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class TestController {

    @GetMapping("/swagger-ui")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}
