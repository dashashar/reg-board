package ru.itis.semester_work.impl.service;

import ru.itis.semester_work.api.dto.request.SignUpRequest;

public interface AccountService {

    void createAccount(SignUpRequest request);

    String signInAccount(String email, String password);

}
