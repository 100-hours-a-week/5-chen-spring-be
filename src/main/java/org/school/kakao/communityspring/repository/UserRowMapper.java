package org.school.kakao.communityspring.repository;

import jakarta.annotation.Nullable;
import org.school.kakao.communityspring.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String profileImage = rs.getString("profile_image");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String nickname = rs.getString("nickname");
        Optional<LocalDateTime> createdAt = transform(rs.getTimestamp("created_at"));
        Optional<LocalDateTime> updatedAt = transform(rs.getTimestamp("updated_at"));


        return User.of(id, profileImage, email, password, nickname, createdAt.orElse(null), updatedAt.orElse(null));
    }

    private Optional<LocalDateTime> transform(@Nullable Timestamp timestamp) {
        if (timestamp == null) {
            return Optional.empty();
        }
        return Optional.of(timestamp.toLocalDateTime());
    }
}
