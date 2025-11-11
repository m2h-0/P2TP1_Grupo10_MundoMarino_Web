package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.service.ItinerarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/itinerarios")
public class ItinerarioController {

    private final ItinerarioService itinerarioService;

    public ItinerarioController(ItinerarioService itinerarioService) {
        this.itinerarioService = itinerarioService;
    }

    @GetMapping
    public String list(@RequestParam(name = "q", required = false) String q, Model model) {
        List<Itinerario> items = itinerarioService.obtenerTodosLosItinerarios();

        model.addAttribute("itinerarios", items);
        model.addAttribute("itinerario", new Itinerario());
        return "admin/itinerarios";
    }

    @PostMapping
    public String create(@RequestParam String descRecorrido,
                         @RequestParam(required = false) Integer longitud,
                         @RequestParam(required = false) Integer maxVisitantes,
                         @RequestParam(required = false) Integer numEspecies,
                         RedirectAttributes redirectAttributes) {

        Itinerario it = new Itinerario();
        it.setDescRecorrido(descRecorrido);
        it.setLongitud(longitud);
        it.setMaxVisitantes(maxVisitantes);
        it.setNumEspecies(numEspecies);

        itinerarioService.crearItinerario(it);
        redirectAttributes.addFlashAttribute("success", "Itinerario creado correctamente");
        return "redirect:/admin/itinerarios";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            itinerarioService.eliminarItinerario(id);
            redirectAttributes.addFlashAttribute("success", "Itinerario eliminado");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: " + ex.getMessage());
        }
        return "redirect:/admin/itinerarios";
    }
}