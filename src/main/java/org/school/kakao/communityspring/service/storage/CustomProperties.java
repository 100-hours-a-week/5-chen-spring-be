package org.school.kakao.communityspring.service.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@EnableConfigurationProperties(CustomProperties.Storage.class)
@Configuration
@RequiredArgsConstructor
public class CustomProperties {
    private final Storage storage;

    public Storage storage() {
        return storage;
    }

    @Getter
    @RequiredArgsConstructor
    @ConfigurationProperties("storage")
    public static class Storage {

        private final String location;

        public Path getPath() {
            return Path.of(location);
        }
    }
}
