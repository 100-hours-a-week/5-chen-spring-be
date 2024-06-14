package org.school.kakao.communityspring.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationFailureHandler failureHandler = new AuthenticationEntryPointFailureHandler(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(JwtConstants.AUTHORIZATION_HEADER);
        if (authorizationHeader == null) {
            logger.debug("Authorization header is empty");
            filterChain.doFilter(request, response);
            return;
        }
        if (!authorizationHeader.startsWith(JwtConstants.BEARER_PREFIX)) {
            throw new BadCredentialsException("Error authentication token");
        }
        try {
            String token = authorizationHeader.substring(7);
            Authentication authentication = convert(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (AuthenticationException exception) {
            logger.debug("AUTHENTICATION FAILED", exception);
            failureHandler.onAuthenticationFailure(request, response, exception);
        }
    }

    private Authentication convert(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JwtUtil.verify(token);
        } catch (JWTVerificationException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }

        String username = JwtUtil.getUsername(decodedJWT);
        CustomUserDetails customUserDetails = new CustomUserDetails(username, null);
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }
}
