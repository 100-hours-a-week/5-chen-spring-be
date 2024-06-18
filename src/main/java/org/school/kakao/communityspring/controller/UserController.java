package org.school.kakao.communityspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.UserLoginResponse;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.dto.UserUpdatePasswordRequest;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.service.AuthService;
import org.school.kakao.communityspring.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@SecurityRequirement(name = "bearer-key")
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/")
    public List<UserResponse> list() {
        return userService.list()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Operation(summary = "Test success")
    @GetMapping("/me")
    public UserLoginResponse me(HttpServletResponse response) {
        return authService.renewAccess(response);
    }

    @Operation(summary = "Test success")
    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse update(
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        User user = userService.update(image, nickname);
        return new UserResponse(user);
    }

    @Operation(summary = "Test success")
    @PutMapping("/me/password")
    public UserResponse updatePassword(@RequestBody UserUpdatePasswordRequest request) {
        User user = userService.updatePassword(request.password());
        return new UserResponse(user);
    }

    @DeleteMapping("/me")
    public Map<String, Boolean> exit() {
        // TODO : 삭제 구현 (인증 구현 이후)
        return Map.of("success", true);
    }
}
