package org.school.kakao.communityspring.controller;

import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.dto.UserModifyRequest;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.dto.UserUpdatePasswordRequest;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public List<UserResponse> list() {
        return userService.list()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @GetMapping("/me")
    public UserResponse me() {
        User me = userService.me();
        return new UserResponse(me);
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse update(@ModelAttribute UserModifyRequest modifyRequest) {
        User user = userService.update(modifyRequest.image(), modifyRequest.nickname());
        return new UserResponse(user);
    }

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
