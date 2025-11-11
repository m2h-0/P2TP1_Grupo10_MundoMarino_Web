package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.service.EmpleadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/empleados")
public class AdminEmpleadosController {

    private final EmpleadoService empleadoService;

    public AdminEmpleadosController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("empleados", empleadoService.listar());
        return "admin/empleados";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "admin/empleado-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/admin/empleados";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") String id, Model model) {
        Empleado e = empleadoService.porId(id).orElse(new Empleado());
        model.addAttribute("empleado", e);
        return "admin/empleado-form";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") String id) {
        empleadoService.borrar(id);
        return "redirect:/admin/empleados";
    }
}

