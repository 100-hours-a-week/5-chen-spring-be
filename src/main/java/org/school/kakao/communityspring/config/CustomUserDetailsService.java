package org.school.kakao.communityspring.config;

import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("USERNAME NOT FOUND");
        }
        User user = optionalUser.get();
        return new CustomUserDetails(user);
    }
}
