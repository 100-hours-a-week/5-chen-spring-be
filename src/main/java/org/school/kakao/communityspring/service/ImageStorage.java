package org.school.kakao.communityspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.service.storage.StorageException;
import org.school.kakao.communityspring.service.storage.CustomProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageStorage {
    private final CustomProperties customProperties;
    private final Environment environment;

    public String store(MultipartFile file) {
        Path storagePath = customProperties.storage().getPath();

        try {
            String hostName = InetAddress.getLocalHost().getHostAddress();
            int port = environment.getProperty("server.port", Integer.class, 8080);
            log.debug("#{}:{}", hostName, port);
        } catch (UnknownHostException ignored) {
        }

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String filename = makeFileName(file);
            Path destinationFile = storagePath
                    .resolve(Paths.get(filename))
                    .normalize()
                    .toAbsolutePath();
            if (!destinationFile.getParent().equals(storagePath.toAbsolutePath())) {
                // This is a security check
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                log.debug("File save to : {}", destinationFile);
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return storagePath.resolve(filename).toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    private static String makeFileName(MultipartFile file) {
        return System.currentTimeMillis() + file.getOriginalFilename();
    }


    public void delete(String path) {
        log.debug("Delete (file = {}", path);
    }
}
