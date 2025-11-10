package com.grupo10.mundomarino.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/cuidador")
@PreAuthorize("hasRole('CUIDADOR')")
public class CuidadorController {

    @GetMapping("/animales")
    public String verAnimales(Model model) {
        model.addAttribute("titulo", "Animales a Cargo");
        return "cuidador/animales";
    }

    @GetMapping("/dashboard")
    public String cuidadorDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Cuidador");
        return "dashboard/cuidador";
    }

    @GetMapping("/editar")
    public String cuidadorEditar(Model model) {
        model.addAttribute("titulo", "Editar Ficha de Animal");
        return "cuidador/editar";
    }
}
