package org.school.kakao.communityspring.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.school.kakao.communityspring.model.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.util.WebUtils;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.NONE)
public class JwtUtil {

    public static boolean hasAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = retrieveAuthorizationHeaderValue(request);
        return authorizationHeader == null;
    }

    public static boolean startsWithBearerToken(HttpServletRequest request) {
        String authorizationHeader = retrieveAuthorizationHeaderValue(request);
        return authorizationHeader != null && authorizationHeader.startsWith(JwtConstants.BEARER_PREFIX);
    }

    private static String retrieveAuthorizationHeaderValue(HttpServletRequest request) {
        return request.getHeader(JwtConstants.AUTHORIZATION_HEADER);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = retrieveAuthorizationHeaderValue(request);
        if (authorizationHeader == null) return null;
        return authorizationHeader.replace(JwtConstants.BEARER_PREFIX, "");
    }

    public static String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie refreshCookie = WebUtils.getCookie(request, "refresh-token");
        if (refreshCookie == null) {
            throw new AuthenticationCredentialsNotFoundException("Refresh token not found");
        }
        return refreshCookie.getValue();
    }

    public static DecodedJWT verifyAccessToken(String token) throws JWTVerificationException {
        return verifyToken(token);
    }

    public static DecodedJWT verifyRefreshToken(String token) throws JWTVerificationException {
        return verifyToken(token);
    }

    private static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(JwtConstants.ALGORITHM)
                .withIssuer(JwtConstants.ISSUER)
                .build();
        return verifier.verify(token);
    }

    public static String issueAccess(User user) {
        return JWT.create()
                .withIssuer(JwtConstants.ISSUER)
                .withClaim(JwtConstants.CLAIM_KEY_EMAIL, user.getEmail())
                .withExpiresAt(Instant.now().plus(JwtConstants.ACCESS_EXPIRATION_TIME))
                .sign(JwtConstants.ALGORITHM);
    }

    public static void issueRefresh(HttpServletResponse response, User user) {
        String refreshToken = issueRefreshInternal(user.getEmail());
        setRefreshTokenToResponse(response, refreshToken);
    }

    public static void renewRefresh(HttpServletResponse response, String email) {
        String refreshToken = issueRefreshInternal(email);
        setRefreshTokenToResponse(response, refreshToken);
    }

    private static String issueRefreshInternal(String email) {
        return JWT.create()
                .withIssuer(JwtConstants.ISSUER)
                .withClaim(JwtConstants.CLAIM_KEY_EMAIL, email)
                .withExpiresAt(Instant.now().plus(JwtConstants.REFRESH_EXPIRATION_TIME))
                .sign(JwtConstants.ALGORITHM);
    }


    private static void setRefreshTokenToResponse(HttpServletResponse response, String refreshToken) {
        int maxAge = (int) JwtConstants.REFRESH_EXPIRATION_TIME.toSeconds();
        Cookie refreshCookie = makeRefreshCookie(refreshToken, maxAge);
        response.addCookie(refreshCookie);
    }

    public static String getUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(JwtConstants.CLAIM_KEY_EMAIL).asString();
    }


    public static void deleteRefresh(HttpServletResponse response) {
        Cookie refreshCookie = makeRefreshCookie(null, 0);
        response.addCookie(refreshCookie);
    }

    private static Cookie makeRefreshCookie(String refreshToken, int maxAgeInSeconds) {
        Cookie refreshCookie = new Cookie("refresh-token", refreshToken);
        refreshCookie.setMaxAge(maxAgeInSeconds);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        return refreshCookie;
    }
}
