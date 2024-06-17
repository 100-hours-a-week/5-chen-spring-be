package org.school.kakao.communityspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.service.storage.CustomProperties;
import org.school.kakao.communityspring.service.storage.StorageException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageStorage {
    private final CustomProperties.Storage storageProperties;

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String filename = makeFileName(file);
            Path imageDiscPath = storageProperties.imageDiscPath();

            Path destinationFile = imageDiscPath.resolve(filename).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(imageDiscPath.toAbsolutePath())) {
                // This is a security check
                log.warn("Destination Path : {}, Image Store Path : {}", destinationFile, imageDiscPath);
                throw new StorageException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                log.debug("File save to : {}", destinationFile);
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            Path url = storageProperties.imageWebPath().resolve(filename);
            return url.toString();
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
