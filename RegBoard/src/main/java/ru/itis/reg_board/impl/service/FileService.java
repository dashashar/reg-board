package ru.itis.reg_board.impl.service;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.reg_board.api.dto.response.ImageUploadResponse;

public interface FileService {

    void init();

    ImageUploadResponse uploadFile(MultipartFile file);

    ImageUploadResponse uploadFile(String fileId, MultipartFile file);

    byte[] getFile(String fileId);

    void deleteFile(String fileId);
}
