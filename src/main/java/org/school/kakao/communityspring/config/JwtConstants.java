package org.school.kakao.communityspring.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class JwtConstants {
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("secret");
    public static final String ISSUER = "community";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

}
