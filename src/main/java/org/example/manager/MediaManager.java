package org.example.manager;

import org.example.dto.UploadSingleMediaResponseDTO;
import org.example.exception.UnsupportedContentTypeException;
import org.example.exception.UploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Component
public class MediaManager {
    private final Path path = Path.of("media");
    private final Map<String, String> types = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/tiff", ".tif"
    );

    public MediaManager() throws IOException {
        Files.createDirectories(path);
    }

    public UploadSingleMediaResponseDTO save(MultipartFile file) {
        try {
            final String name = generateName(file.getContentType());
            file.transferTo(path.resolve(name));
            return new UploadSingleMediaResponseDTO(name);
        } catch (IOException e) {
            throw new UploadException(e);
        }
    }

    private String generateName(String contentType) {
        return UUID.randomUUID() + getExtension(contentType);
    }

    private String getExtension(String contentType) {
        final String extension = types.get(contentType);
        if (extension == null) {
            throw new UnsupportedContentTypeException(contentType + " not allowed for upload");
        }
        return extension;
    }
}
