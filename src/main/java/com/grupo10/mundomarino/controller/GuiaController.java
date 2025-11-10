package com.grupo10.mundomarino.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guia")
@PreAuthorize("hasRole('GUIA')") 
public class GuiaController {
    
    @GetMapping("/itinerarios")
    public String verItinerarios(Model model) {
        model.addAttribute("titulo", "Mis Itinerarios - Mundo Marino");
        return "guia/itinerarios";
    }
}
