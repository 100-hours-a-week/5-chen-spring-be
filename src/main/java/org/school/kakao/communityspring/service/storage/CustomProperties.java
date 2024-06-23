package org.school.kakao.communityspring.service.storage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

@Slf4j
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
        private final String serverName;

        public Path storageWebPath() {
            return Path.of(urlPrefix, "/**");
        }

        public Path imageDiscPath() {
            return Path.of(directoryPath, imagePath);
        }

        public URI imageWebUrl() throws URISyntaxException {
            Path path = Path.of(urlPrefix, imagePath);
            return new URI(serverName + path + "/");
        }

        public Path imageWebPath() {
            return Path.of(serverName, urlPrefix, imagePath);
        }
    }
}
