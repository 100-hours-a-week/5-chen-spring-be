package org.school.kakao.communityspring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.dto.UserLoginRequest;
import org.school.kakao.communityspring.dto.UserRegisterRequest;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse register(@ModelAttribute UserRegisterRequest registerRequest) {
        User user = authService.signUp(registerRequest.file(), registerRequest.email(), registerRequest.password(), registerRequest.nickname());
        return new UserResponse(user);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        System.out.println("AuthController.login");
        log.debug("Login request: {}", loginRequest);
        Optional<User> optionalUser = authService.login(loginRequest.email(), loginRequest.password(), response);
        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        return new UserResponse(user);
    }
}
