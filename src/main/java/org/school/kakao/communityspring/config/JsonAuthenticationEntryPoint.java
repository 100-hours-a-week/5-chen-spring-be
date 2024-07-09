package org.school.kakao.communityspring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        log.debug("AuthenticationEntryPoint-Exception " + authException.getMessage(), authException.fillInStackTrace());
        String jsonResponse;
        String messageKey = "message";
        if (authException instanceof InsufficientAuthenticationException) {
            jsonResponse = objectMapper.writeValueAsString(
                    Map.of(messageKey, "Not Found")
            );
        } else if (authException instanceof BadCredentialsException) {
            jsonResponse = objectMapper.writeValueAsString(
                    Map.of(messageKey, "토큰이 올바르지 않습니다.")
            );
        } else {
            jsonResponse = objectMapper.writeValueAsString(
                    Map.of(messageKey, authException.getMessage())
            );
        }

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
