package org.school.kakao.communityspring.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.NONE)
public class JwtConstants {
    public static final String SECRET = "kakao-community-spring";
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    public static final String ISSUER = "community";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CLAIM_KEY_EMAIL = "email";
    public static final Duration REFRESH_EXPIRATION_TIME = Duration.ofDays(1);
    public static final Duration ACCESS_EXPIRATION_TIME = Duration.ofHours(6);
}
