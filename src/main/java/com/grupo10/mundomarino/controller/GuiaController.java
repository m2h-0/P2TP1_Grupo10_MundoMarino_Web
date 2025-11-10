package com.grupo10.mundomarino.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/dashboard")
    public String guiaDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Guía");
        return "dashboard/guia";
    }

    @GetMapping("/recorridos")
    public String guiaRecorridos(Model model) {
        model.addAttribute("titulo", "Recorridos");
        return "guia/recorridos";
    }
}
