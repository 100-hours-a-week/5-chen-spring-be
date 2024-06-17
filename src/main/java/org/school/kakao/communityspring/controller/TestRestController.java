package org.school.kakao.communityspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school.kakao.communityspring.service.ImageStorage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestRestController {
    private final ImageStorage imageStorage;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> test(@RequestPart("file") MultipartFile file) {
        String savedPath = imageStorage.store(file);
        return Map.of("saved", savedPath);
    }
}
