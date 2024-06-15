package org.school.kakao.communityspring.service;

import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.model.User;
import org.school.kakao.communityspring.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthContextUtil {
    private final UserRepository userRepository;


    public User loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());

        return optionalUser.orElseThrow(() -> new BadCredentialsException("Not found"));
    }
}
