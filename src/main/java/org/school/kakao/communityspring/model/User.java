package org.school.kakao.communityspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String profileImage;
    private String email;
    @JsonIgnore
    @Getter(AccessLevel.PROTECTED)
    private String password;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private User(Long id, String profileImage, String email, String password, String nickname, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.profileImage = profileImage;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static User create(String profileImage, String email, String password, String nickname) {
        return new User(null, profileImage, email, password, nickname, LocalDateTime.now(), null, null);
    }

    public static User of(Long id, String profileImage, String email, String password, String nickname, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(id, profileImage, email, password, nickname, createdAt, updatedAt, null);
    }

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    public void updateId(Long id) {
        this.id = id;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfimeImage(String path) {
        this.profileImage = path;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}