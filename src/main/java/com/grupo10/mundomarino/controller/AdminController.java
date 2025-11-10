package com.grupo10.mundomarino.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("titulo", "Panel de Administración - Mundo Marino");
        return "admin/dashboard";
    }
    
    @GetMapping("/especies")
    public String gestionEspecies() {
        return "admin/especies";
    }
    
    @GetMapping("/empleados")
    public String gestionEmpleados() {
        return "admin/empleados";
    }
}
