package org.school.kakao.communityspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtConfiguration {
    private final JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(
                new OrRequestMatcher(List.of(
                        AntPathRequestMatcher.antMatcher("/posts/**"),
                        AntPathRequestMatcher.antMatcher("/users/**"),
                        AntPathRequestMatcher.antMatcher("/comments/**"))
                ),
                new AuthenticationEntryPointFailureHandler(jsonAuthenticationEntryPoint)
        );
    }
}
