package com.grupo10.mundomarino.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UsuarioViewController {
    // Página de registro de usuario
    @GetMapping("/registro")
    public String registro() {
        log.info("Accediendo a la página de registro de usuario");
        return "registro";
    }

    // Página de listado de usuarios
    @GetMapping("/usuarios")
    public String usuarios() {
        log.info("Accediendo al listado de usuarios");
        return "usuarios";
    }
}
