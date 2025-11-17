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
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin/especies")
@PreAuthorize("hasRole('ADMIN')")
public class EspecieController {

    private final EspecieService especieService;
    private final ZonaRepository zonaRepository;

    public EspecieController(EspecieService especieService, ZonaRepository zonaRepository) {
        this.especieService = especieService;
        this.zonaRepository = zonaRepository;
    }

    // List species
    @GetMapping
    public String listar(Model model) {
        List<Especie> especies = especieService.obtenerTodasLasEspecies();
        List<Zona> zonas = zonaRepository.findAll();

        model.addAttribute("especies", especies);
        model.addAttribute("zonas", zonas);
        return "admin/especies";
    }

    // Show form for new species
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        List<Zona> zonas = zonaRepository.findAll();
        model.addAttribute("especie", new Especie());
        model.addAttribute("zonas", zonas);
        return "admin/especie-form";
    }

    // Show form for editing species
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Especie especie = especieService.obtenerEspeciePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada: " + id));
        List<Zona> zonas = zonaRepository.findAll();

        model.addAttribute("especie", especie);
        model.addAttribute("zonas", zonas);
        return "admin/especie-form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(value = "nombre", required = true) String nombre,
            @RequestParam(value = "nombreCientifico", required = false) String nombreCientifico,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "zonaId", required = false) Integer zonaId,
            @RequestParam(value = "idEspecie", required = false) Integer idEspecie,
            RedirectAttributes redirectAttributes) {
        try {
            Especie especie = new Especie();
            especie.setNombre(nombre);
            especie.setNombreCientifico(nombreCientifico);
            especie.setDescripcion(descripcion);

            if (zonaId != null && zonaId > 0) {
                Zona zona = zonaRepository.findById(zonaId).orElse(null);
                especie.setZona(zona);
            }

            if (idEspecie != null && idEspecie > 0) {
                // Update
                especie.setIdEspecie(idEspecie);
                especieService.actualizarEspecie(idEspecie, especie);
                redirectAttributes.addFlashAttribute("success", "Especie actualizada correctamente");
            } else {
                // Create
                especieService.crearEspecie(especie);
                redirectAttributes.addFlashAttribute("success", "Especie creada correctamente");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error: " + ex.getMessage());
            return "redirect:/admin/especies/nuevo";
        }
        return "redirect:/admin/especies";

    }

    // Delete species
    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            especieService.eliminarEspecie(id);
            redirectAttributes.addFlashAttribute("success", "Especie eliminada correctamente");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la especie: " + ex.getMessage());
        }
        return "redirect:/admin/especies";
    }
}
