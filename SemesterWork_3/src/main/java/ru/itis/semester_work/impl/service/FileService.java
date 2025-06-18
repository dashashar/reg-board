package ru.itis.semester_work.impl.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.semester_work.api.dto.response.ImageUploadResponse;

public interface FileService {

    void init();

    ImageUploadResponse uploadFile(MultipartFile file);

    ImageUploadResponse uploadFile(String fileId, MultipartFile file);

    byte[] getFile(String fileId);

    void deleteFile(String fileId);
}
