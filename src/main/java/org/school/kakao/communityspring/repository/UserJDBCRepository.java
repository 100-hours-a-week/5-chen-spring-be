package org.school.kakao.communityspring.repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.school.kakao.communityspring.model.User;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJDBCRepository implements UserRepository {
    private final JdbcClient jdbcClient;
    private final UserRowMapper userRowMapper;

    @Override
    public List<User> listAll() {
        return jdbcClient
                .sql("SELECT * FROM User WHERE deleted_at IS NULL ORDER BY created_at DESC")
                .query(userRowMapper)
                .list();
    }

    @Override
    public User findById(Long id) {

        String sql = "SELECT * FROM User WHERE id = :id AND deleted_at IS NULL ORDER BY created_at DESC";
        Optional<User> optionalUser = jdbcClient
                .sql(sql)
                .params(Map.of("id", id))
                .query(userRowMapper)
                .optional();
        return optionalUser.orElseThrow(() -> new EntityNotFoundException("User Not Found ID:" + id));
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            String insertSQL = """
                    INSERT INTO User (profile_image,email,password,nickname,created_at)
                    VALUES (:profileImage,:email,:password,:nickname,:createdAt)
                    """;
            jdbcClient
                    .sql(insertSQL)
                    .paramSource(new BeanPropertySqlParameterSource(user))
                    .update(keyHolder);
            user.updateId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return user;
        }
        String updateSQL = """
                UPDATE User
                SET profile_image = :profile_image,
                    email         = :email,
                    password      = :password,
                    nickname      = :nickname,
                    created_at    = :created_at,
                    updated_at    = :updated_at,
                    deleted_at    = :deleted_at
                WHERE id = :id
                """;
        jdbcClient
                .sql(updateSQL)
                .paramSource(new BeanPropertySqlParameterSource(user))
                .update();
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        jdbcClient
                .sql("DELETE FROM User WHERE id = :id")
                .paramSource(Map.of("id", id))
                .update();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcClient
                .sql("SELECT * FROM User WHERE email LIKE :email")
                .paramSource(Map.of("email", email))
                .query(userRowMapper).optional();
    }
}
