package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Animal;
import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.service.AnimalService;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/animales")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAnimalController {

    private final AnimalService animalService;
    private final EmpleadoRepository empleadoRepo;

    public AdminAnimalController(AnimalService animalService, EmpleadoRepository empleadoRepo) {
        this.animalService = animalService;
        this.empleadoRepo = empleadoRepo;
    }

    // GET /admin/animales
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("animales", animalService.listar());
        return "admin/animales-list";
    }

    // GET /admin/animales/asignar/{id}
    @GetMapping("/asignar/{id}")
    public String asignarForm(@PathVariable Integer id, Model model) {
        Animal animal = animalService.porId(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + id));
        List<Empleado> cuidadores = empleadoRepo.findByTipo("CUIDADOR");
        model.addAttribute("animal", animal);
        model.addAttribute("cuidadores", cuidadores);
        return "admin/animal-asignar";
    }

    // POST /admin/animales/asignar
    @PostMapping("/asignar")
    public String asignarPost(@RequestParam Integer idAnimal, @RequestParam String idCuidador, Model model) {
        try {
            animalService.asignarCuidador(idAnimal, idCuidador);
            return "redirect:/admin/animales?asignado";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("animal", animalService.porId(idAnimal).orElse(null));
            model.addAttribute("cuidadores", empleadoRepo.findByTipo("CUIDADOR"));
            return "admin/animal-asignar";
        }
    }
}
