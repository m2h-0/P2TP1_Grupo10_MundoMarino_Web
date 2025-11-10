package com.grupo10.mundomarino.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ViewController {
    // Página principal (index)
    @GetMapping({"/", "/index"})
    public String index() {
        log.info("Accediendo a la página principal");
        return "index";// Spring Boot busca templates/index.html (por Thymeleaf)
    }

    // Página de login
    @GetMapping("/login")
    public String login() {
        log.info("Accediendo a la página de login");
        return "login"; // busca templates/login.html
    }
}
