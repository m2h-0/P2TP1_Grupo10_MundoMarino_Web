package com.grupo10.mundomarino.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ContactoViewController {
    // Página de lista de contactos
    @GetMapping("/lista-contactos")
    public String listaContactos() {
        log.info("Accediendo a la lista de contactos");
        return "lista-contactos";
    }

    // Página de agregar contacto
    @GetMapping("/agregar-contacto")
    public String agregarContacto() {
        log.info("Accediendo a agregar contacto");
        return "agregar-contacto";
    }
}