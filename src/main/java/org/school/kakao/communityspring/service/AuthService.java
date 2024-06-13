package org.school.kakao.communityspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.config.JwtUtil;
import org.school.kakao.communityspring.dto.UserRegisterRequest;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ImageStorage imageStorage;

    public Optional<User> login(String email, String password, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.debug("Incorrect Email");
            return Optional.empty();
        }
        User user = optionalUser.get();
        if (!user.checkPassword(password)) {
            log.debug("Incorrect password");
            return Optional.empty();
        }

        String token = JwtUtil.issue(user);
        JwtUtil.setHeader(response, token);

        return Optional.of(user);
    }

    public User signUp(MultipartFile profileImage, String email, String password, String nickname) {
        String path = imageStorage.save(profileImage);
        User user = User.create(path, email, password, nickname);
        return userRepository.save(user);
    }
}
