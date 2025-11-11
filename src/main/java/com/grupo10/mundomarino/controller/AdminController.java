package com.grupo10.mundomarino.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "dashboard/admin";
    }

    @GetMapping("/especies")
    public String gestionEspecies(Model model) {
        model.addAttribute("titulo", "Gestión de Especies");
        return "admin/especies";
    }

    
}