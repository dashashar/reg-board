package ru.itis.reg_board.impl.service;

import java.util.UUID;

public interface QrGenerationService {

    void generateAndSaveQrCodeForRegistration(UUID registrationId);

    byte[] generateQrCode(String content);

    void deleteQrCodeForRegistration(UUID registrationId);
}
