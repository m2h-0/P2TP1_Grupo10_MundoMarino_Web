package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Habitat;
import com.grupo10.mundomarino.service.HabitatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/habitats")
public class HabitatController {

    private final HabitatService habitatService;

    public HabitatController(HabitatService habitatService) {
        this.habitatService = habitatService;
    }

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        List<Habitat> habitats = (q != null && !q.isBlank())
                ? habitatService.buscarPorNombre(q)
                : habitatService.obtenerTodosLosHabitats();

        model.addAttribute("habitats", habitats);
        // provide an empty example object if template expects one
        model.addAttribute("habitat", new Habitat());
        return "admin/habitats";
    }

    @PostMapping
    public String create(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        Habitat h = new Habitat();
        h.setNombre(nombre);
        habitatService.crearHabitat(h);
        redirectAttributes.addFlashAttribute("success", "Habitat creado correctamente");
        return "redirect:/admin/habitats";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            habitatService.eliminarHabitat(id);
            redirectAttributes.addFlashAttribute("success", "Habitat eliminado");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: " + ex.getMessage());
        }
        return "redirect:/admin/habitats";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        model.addAttribute("habitats", habitatService.buscarPorNombre(q));
        model.addAttribute("habitat", new Habitat());
        return "admin/habitats";
    }
}