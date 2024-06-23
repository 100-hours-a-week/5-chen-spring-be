package org.school.kakao.communityspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.dto.UserLoginRequest;
import org.school.kakao.communityspring.dto.UserLoginResponse;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
            @RequestPart("nickname") String nickname,
            HttpServletResponse response
    ) {
        User user = authService.signUp(image, email, password, nickname);
        response.setStatus(HttpStatus.CREATED.value());
        return new UserResponse(user);
    }

    @Operation(summary = "Test Success")
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest, HttpServletResponse response) {
        log.debug("Login request: {}", loginRequest);
        return authService.login(loginRequest.email(), loginRequest.password(), response);
    }

    @PostMapping("/refresh")
    public UserLoginResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.loginByRefreshToken(request, response);
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletResponse response) {
        log.debug("Logout request");
        authService.logout(response);
        return Map.of("message", "OK");
    }
}
