package ru.itis.semester_work.api.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.semester_work.api.dto.request.SignUpRequest;
import ru.itis.semester_work.api.dto.request.SignInRequest;

@RequestMapping("/account")
public interface AccountApi {

    @GetMapping("/signUp")
    String showRegistrationForm();

    @PostMapping("/signUp")
    String signUp(@Valid @ModelAttribute("request") SignUpRequest request,
                  BindingResult bindingResult, Model model);

    @GetMapping("/signIn")
    String showLoginForm();

    @PostMapping("/signIn")
    String signIn(@Valid @ModelAttribute("request") SignInRequest request, BindingResult bindingResult,
                  HttpServletResponse response, Model model);

}
