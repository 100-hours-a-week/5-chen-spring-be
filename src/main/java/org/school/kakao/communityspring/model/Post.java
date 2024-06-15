package org.school.kakao.communityspring.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Post")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String image;
    @Column
    private String title;
    @Column
    private String content;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String image, String title, String content, User user) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }
}
