package ru.itis.semester_work.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.semester_work.api.api.ImageApi;
import ru.itis.semester_work.api.dto.response.ImageUploadResponse;
import ru.itis.semester_work.impl.service.FileService;

@RestController
@RequiredArgsConstructor
public class ImageController implements ImageApi {

    private final FileService fileService;

    @Override
    public ResponseEntity<byte[]> getImage(String fileId) {
        byte[] imageData = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @Override
    public void deleteImage(String fileId) {
        fileService.deleteFile(fileId);
    }
}
