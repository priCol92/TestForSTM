package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UploadSingleMediaResponseDTO;
import org.example.manager.MediaManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaManager manager;

    @PostMapping("/multipart")
    public UploadSingleMediaResponseDTO upload(@RequestPart MultipartFile file) {
        return manager.save(file);
    }

}
