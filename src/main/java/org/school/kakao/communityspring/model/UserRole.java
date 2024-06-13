package org.school.kakao.communityspring.model;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER");
    private final String value;
    private final String role;

    UserRole(String value) {
        this.value = value;
        this.role = "ROLE_" + value;
    }
}
