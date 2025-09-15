package ru.itis.reg_board.impl.service.impl;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.reg_board.api.dto.response.ImageUploadResponse;
import ru.itis.reg_board.impl.exception.MinioException;
import ru.itis.reg_board.impl.service.FileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements FileService {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @PostConstruct
    @Override
    public void init() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());
            if (!found) {
                log.info("Bucket {} not found, creating...", bucket);
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            }
        } catch (Exception e) {
            throw new MinioException("Ошибка при инициализации MinIO", e);
        }
    }

    @Override
    public ImageUploadResponse uploadFile(MultipartFile file) {
        String fileId = UUID.randomUUID().toString();
        return uploadFile(fileId, file);
    }

    @Override
    public ImageUploadResponse uploadFile(String fileId, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileId)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType()
                            ).build()
            );
            return new ImageUploadResponse(fileId);
        } catch (Exception e) {
            log.error("Не удалось загрузить файл: {}", e.getMessage());
            throw new MinioException("Не удалось загрузить файл", e);
        }
    }

    @Override
    public byte[] getFile(String fileId) {
        try {
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileId)
                            .build()
            );
            return response.readAllBytes();
        } catch (Exception e) {
            log.error("Не удалось получить файл: {}", e.getMessage());
            throw new MinioException("Не удалось получить файл", e);
        }
    }

    @Override
    public void deleteFile(String fileId) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileId)
                            .build()
            );
            log.info("Файл {} успешно удален", fileId);
        } catch (Exception e) {
            log.error("Не удалось удалить файл {}: {}", fileId, e.getMessage());
            throw new MinioException("Не удалось удалить файл", e);
        }
    }
}
