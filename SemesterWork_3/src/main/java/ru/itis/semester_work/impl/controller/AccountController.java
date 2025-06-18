package ru.itis.semester_work.impl.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.semester_work.api.api.AccountApi;
import ru.itis.semester_work.api.dto.request.SignUpRequest;
import ru.itis.semester_work.api.dto.request.SignInRequest;
import ru.itis.semester_work.impl.service.AccountService;
import ru.itis.semester_work.impl.util.ValidationUtil;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @Override
    public String showRegistrationForm() {
        return "account/sign_up";
    }

    @Override
    public String signUp(SignUpRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", ValidationUtil.getValidationErrors(bindingResult));
            model.addAttribute("account", request);
            return "account/sign_up";
        }
        accountService.createAccount(request);
        return "redirect:/account/signIn";
    }

    @Override
    public String showLoginForm() {
        return "account/sign_in";
    }

    @Override
    public String signIn(SignInRequest request, BindingResult bindingResult,
                         HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtil.getValidationErrors(bindingResult);
            model.addAttribute("errors", errors);
            return "account/sign_in";
        }
        String token = accountService.signInAccount(request.email(), request.password());

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "redirect:/event";
    }

}
