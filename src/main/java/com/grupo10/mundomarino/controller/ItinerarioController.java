package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.service.ItinerarioService;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/itinerarios")
@PreAuthorize("hasRole('ADMIN')")
public class ItinerarioController {

    private final ItinerarioService itinerarioService;
    private final EmpleadoRepository empleadoRepo;

    public ItinerarioController(ItinerarioService itinerarioService, EmpleadoRepository empleadoRepo) {
        this.itinerarioService = itinerarioService;
        this.empleadoRepo = empleadoRepo;
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

    // GET /admin/itinerarios/asignar/{id}
    @GetMapping("/asignar/{id}")
    public String asignarForm(@PathVariable Integer id, Model model) {
        Itinerario itin = itinerarioService.obtenerItinerarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + id));
        java.util.List<com.grupo10.mundomarino.entity.Empleado> guias = empleadoRepo.findByTipo("GUIA");
        model.addAttribute("itinerario", itin);
        model.addAttribute("guias", guias);
        return "admin/itinerario-asignar";
    }

    // POST /admin/itinerarios/asignar
    @PostMapping("/asignar")
    public String asignarPost(@RequestParam Integer idItinerario, @RequestParam String idGuia, Model model) {
        try {
            itinerarioService.asignarGuia(idItinerario, idGuia);
            return "redirect:/admin/itinerarios?asignado";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("itinerario", itinerarioService.obtenerItinerarioPorId(idItinerario).orElse(null));
            model.addAttribute("guias", empleadoRepo.findByTipo("GUIA"));
            return "admin/itinerario-asignar";
        }
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