package org.school.kakao.communityspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageStorage {
    public String save(MultipartFile file) {
        log.debug("Save(file = {})", file.getOriginalFilename());
        return "save_path";
    }

    public void delete(String path) {
        log.debug("Delete (file = {}", path);
    }
}
