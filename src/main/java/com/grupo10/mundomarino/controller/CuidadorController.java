package com.grupo10.mundomarino.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@RequestMapping("/cuidador")
@PreAuthorize("hasRole('CUIDADOR')")
public class CuidadorController {
    
    @GetMapping("/animales")
    public String verAnimales(Model model) {
        model.addAttribute("titulo", "Animales a Cargo - Mundo Marino");
        return "cuidador/animales";
    }
}
