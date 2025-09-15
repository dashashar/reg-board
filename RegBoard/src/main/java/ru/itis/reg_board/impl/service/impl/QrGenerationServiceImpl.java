package ru.itis.reg_board.impl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.reg_board.impl.exception.QrCodeGenerationException;
import ru.itis.reg_board.impl.service.FileService;
import ru.itis.reg_board.impl.service.QrGenerationService;
import ru.itis.reg_board.impl.util.ByteArrayMultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrGenerationServiceImpl implements QrGenerationService {

    private final RestTemplate restTemplate;
    private final FileService fileService;

    @Value("${api.qr.url}")
    private String qrCodeApiUrl;

    @Value("${api.qr.size}")
    private String qrSize;

    @Value("${server-address}")
    private String serverAddress;

    @Override
    public void generateAndSaveQrCodeForRegistration(UUID registrationId) {
        String confirmRegistration = "%s/registration/%s".formatted(serverAddress, registrationId);
        byte[] qrCodeBytes = generateQrCode(confirmRegistration);
        MultipartFile qrCodeFile = new ByteArrayMultipartFile(
                "qr-code.png", "qr-code.png", "image/png", qrCodeBytes);
        fileService.uploadFile(registrationId.toString(), qrCodeFile);
    }

    @Override
    public byte[] generateQrCode(String content){
        String qrCodeUrl = "%s?size=%s&data=%s".formatted(qrCodeApiUrl, qrSize, content);
        ResponseEntity<byte[]> response = restTemplate.getForEntity(qrCodeUrl, byte[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            return response.getBody();
        } else {
            throw new QrCodeGenerationException("Не удалось получить QR-код из сервиса генерации. Статус: %s"
                    .formatted(response.getStatusCode()));
        }
    }

    @Override
    public void deleteQrCodeForRegistration(UUID registrationId) {
        fileService.deleteFile(registrationId.toString());
    }

}
