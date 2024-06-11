package org.school.kakao.communityspring.repository;

import org.school.kakao.communityspring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> listAll();
    User findById(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> findByEmail(String email);
}
