package org.school.kakao.communityspring.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.school.kakao.communityspring.model.User;

@NoArgsConstructor(access = AccessLevel.NONE)
public class JwtUtil {


    public static DecodedJWT verify(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(JwtConstants.ALGORITHM)
                .withIssuer(JwtConstants.ISSUER)
                .build();
        return verifier.verify(token);
    }

    public static String issue(User user) {
        return JWT.create()
                .withIssuer(JwtConstants.ISSUER)
                .withClaim("email", user.getEmail())
                .sign(JwtConstants.ALGORITHM);
    }

    public static String getUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("email").asString();
    }

    public static void setHeader(HttpServletResponse response, String token) {
        response.setHeader(JwtConstants.AUTHORIZATION_HEADER, JwtConstants.BEARER_PREFIX + token);
    }


}
