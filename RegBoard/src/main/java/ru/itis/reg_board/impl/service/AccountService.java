package ru.itis.reg_board.impl.service;

import ru.itis.reg_board.api.dto.request.SignUpRequest;

public interface AccountService {

    void createAccount(SignUpRequest request);

    String signInAccount(String email, String password);

}
