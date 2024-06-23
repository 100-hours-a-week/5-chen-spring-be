package org.school.kakao.communityspring.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.config.JwtUtil;
import org.school.kakao.communityspring.dto.UserLoginResponse;
import org.school.kakao.communityspring.dto.UserResponse;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ImageStorage imageStorage;
    private final AuthContextUtil authContextUtil;

    public UserLoginResponse loginByRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenString = JwtUtil.getRefreshTokenFromRequest(request);
        DecodedJWT decodedJWT = JwtUtil.verifyRefreshToken(refreshTokenString);
        String email = JwtUtil.getUsername(decodedJWT);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(email));

        String accessToken = JwtUtil.issueAccess(user);
        JwtUtil.issueRefresh(response, user);
        return new UserLoginResponse(accessToken, new UserResponse(user));
    }

    public UserLoginResponse login(String email, String password, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.debug("Incorrect Email");
            throw new UsernameNotFoundException("Incorrect Email or Password");
        }
        User user = optionalUser.get();
        if (!user.checkPassword(password)) {
            log.debug("Incorrect password");
            throw new UsernameNotFoundException("Incorrect Email or Password");
        }

        JwtUtil.issueRefresh(response, user);

        String token = JwtUtil.issueAccess(user);
        return new UserLoginResponse(token, new UserResponse(user));
    }

    public void logout(HttpServletResponse response) {
        JwtUtil.deleteRefresh(response);
    }

    public User signUp(MultipartFile profileImage, String email, String password, String nickname) {
        String path = imageStorage.store(profileImage);
        User user = User.create(path, email, password, nickname);
        return userRepository.save(user);
    }
}
