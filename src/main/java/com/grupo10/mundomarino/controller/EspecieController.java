package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Zona;
import com.grupo10.mundomarino.repository.ZonaRepository;
import com.grupo10.mundomarino.service.EspecieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/especies")
public class EspecieController {

    private final EspecieService especieService;
    private final ZonaRepository zonaRepository;

    public EspecieController(EspecieService especieService, ZonaRepository zonaRepository) {
        this.especieService = especieService;
        this.zonaRepository = zonaRepository;
    }

    // List (and default view)
    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        List<com.grupo10.mundomarino.entity.Especie> especies;
        if (q != null && !q.isBlank()) {
            especies = especieService.buscarPorNombre(q);
        } else {
            especies = especieService.obtenerTodasLasEspecies();
        }

        List<Zona> zonas = zonaRepository.findAll();

        model.addAttribute("especies", especies);
        model.addAttribute("zonas", zonas);
        // ensure a target object for th:object="${especie}" in the template
        model.addAttribute("especie", new Especie());
        return "admin/especies";
    }

    // Create (form posts nombre, descripcion, zonaId)
    @PostMapping("guardar")
    public String create(@RequestParam String nombre,
                         @RequestParam(required = false) String descripcion,
                         @RequestParam(name = "zonaId", required = false) Integer zonaId,
                         RedirectAttributes redirectAttributes) {

        Zona zona = null;
        if (zonaId != null) {
            zona = zonaRepository.findById(zonaId).orElse(null);
        }
        // default zona: first zona from DB if none provided/found
        if (zona == null) {
            zona = zonaRepository.findAll().stream().findFirst().orElse(null);
        }

        Especie e = new Especie();
        e.setNombre(nombre);
        e.setDescripcion(descripcion);
        e.setZona(zona);

        especieService.crearEspecie(e);

        redirectAttributes.addFlashAttribute("success", "Especie creada correctamente");
        return "redirect:/admin/especies";
    }

    // Delete (form posts to /admin/especies/{id}/delete)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            especieService.eliminarEspecie(id);
            redirectAttributes.addFlashAttribute("success", "Especie eliminada");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: " + ex.getMessage());
        }
        return "redirect:/admin/especies";
    }

    // Simple search endpoint (optional, same as GET /admin/especies?q=)
    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Especie> resultados = especieService.buscarPorNombre(q);
        model.addAttribute("especies", resultados);
        model.addAttribute("zonas", zonaRepository.findAll());
        // also provide an empty especie for the form binding
        model.addAttribute("especie", new Especie());
        return "admin/especies";
    }
}