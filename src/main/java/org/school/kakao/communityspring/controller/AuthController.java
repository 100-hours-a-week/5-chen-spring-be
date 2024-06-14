package org.school.kakao.communityspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.dto.UserLoginRequest;
import org.school.kakao.communityspring.dto.UserLoginResponse;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Test Success")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse register(
            @RequestPart("image") MultipartFile image,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("nickname") String nickname
    ) {
        User user = authService.signUp(image, email, password, nickname);
        return new UserResponse(user);
    }

    @Operation(summary = "Test Success")
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        log.debug("Login request: {}", loginRequest);
        return authService.login(loginRequest.email(), loginRequest.password(), response);
    }
}
