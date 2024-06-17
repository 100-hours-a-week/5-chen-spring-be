package org.school.kakao.communityspring.service.storage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@EnableConfigurationProperties(CustomProperties.Storage.class)
@Configuration
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomProperties {

    @RequiredArgsConstructor
    @ConfigurationProperties("storage")
    public static class Storage {
        private final String directoryPath;
        private final String imagePath;
        private final String urlPrefix;

        public Path storageWebPath() {
            return Path.of(urlPrefix, "/**");
        }

        public Path imageDiscPath() {
            return Path.of(directoryPath, imagePath);
        }

        public Path imageWebPath() {
            return Path.of(urlPrefix, imagePath);
        }
    }
}
