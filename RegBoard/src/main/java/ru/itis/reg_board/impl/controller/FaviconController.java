package ru.itis.reg_board.impl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaviconController {
    @GetMapping("/favicon.ico")
    public String redirectFavicon() {
        return "forward:/static/favicon.ico";
    }
}
