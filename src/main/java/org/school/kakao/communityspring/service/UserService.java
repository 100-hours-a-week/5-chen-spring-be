package org.school.kakao.communityspring.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.UserJDBCRepository;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageStorage imageStorage;
    private final UserJDBCRepository userJDBCRepository;


    @Transactional
    public User update(@Nullable MultipartFile image, @Nullable String nickname) {
        User user = meInternal();

        if (image != null) {
            updateImage(user, image);
        }

        if (nickname != null) {
            user.updateNickname(nickname);
        }

        return userRepository.save(user);
    }

    private void updateImage(User user, MultipartFile image) {
        String path = imageStorage.save(image);
        String prevPath = user.getProfileImage();
        imageStorage.delete(prevPath);
        user.updateProfimeImage(path);
    }

    public List<User> list() {
        return userRepository.listAll();
    }

    public User me() {
        return meInternal();
    }

    private User meInternal() {
        // TODO : 로그인 유저 찾기 (인증 구현 이후)
        return userRepository.findById(1L);
    }

    public User updatePassword(String password) {
        User user = meInternal();
        user.updatePassword(password);
        userJDBCRepository.save(user);
        return user;
    }
}
