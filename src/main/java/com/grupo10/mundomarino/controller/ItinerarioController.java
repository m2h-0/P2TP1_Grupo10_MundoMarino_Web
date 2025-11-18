package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.service.ItinerarioService;
import com.grupo10.mundomarino.service.ItinerarioEspecieService;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import com.grupo10.mundomarino.repository.EspecieRepository;
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
    private final ItinerarioEspecieService itinerarioEspecieService;
    private final EmpleadoRepository empleadoRepo;
    private final EspecieRepository especieRepo;

    public ItinerarioController(ItinerarioService itinerarioService, 
                               ItinerarioEspecieService itinerarioEspecieService,
                               EmpleadoRepository empleadoRepo,
                               EspecieRepository especieRepo) {
        this.itinerarioService = itinerarioService;
        this.itinerarioEspecieService = itinerarioEspecieService;
        this.empleadoRepo = empleadoRepo;
        this.especieRepo = especieRepo;
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
                         RedirectAttributes redirectAttributes) {

        Itinerario it = new Itinerario();
        it.setDescRecorrido(descRecorrido);
        it.setLongitud(longitud);
        it.setMaxVisitantes(maxVisitantes);
        // numEspecies se calcula automáticamente desde itinerarioEspecies

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

    // GET /admin/itinerarios/especies/{id}
    @GetMapping("/especies/{id}")
    public String asignarEspeciesForm(@PathVariable Integer id, Model model) {
        Itinerario itin = itinerarioService.obtenerItinerarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + id));
        java.util.List<com.grupo10.mundomarino.entity.Especie> todasEspecies = especieRepo.findAll();
        java.util.List<com.grupo10.mundomarino.entity.Especie> especiesAsignadas = itinerarioEspecieService.obtenerEspeciesPorItinerario(id);
        model.addAttribute("itinerario", itin);
        model.addAttribute("todasEspecies", todasEspecies);
        model.addAttribute("especiesAsignadas", especiesAsignadas);
        return "admin/itinerario-asignar-especies";
    }

    // POST /admin/itinerarios/especies/vincular
    @PostMapping("/especies/vincular")
    public String vincularEspecie(@RequestParam Integer idItinerario, @RequestParam Integer idEspecie, RedirectAttributes redirectAttributes) {
        try {
            itinerarioEspecieService.vincularEspecieAItinerario(idItinerario, idEspecie);
            // Actualizar numEspecies
            Integer cantidadEspecies = itinerarioEspecieService.contarEspeciesPorItinerario(idItinerario);
            Itinerario itin = itinerarioService.obtenerItinerarioPorId(idItinerario).get();
            itin.setNumEspecies(cantidadEspecies);
            itinerarioService.actualizarItinerario(idItinerario, itin);
            redirectAttributes.addFlashAttribute("success", "Especie vinculada correctamente");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error al vincular especie: " + ex.getMessage());
        }
        return "redirect:/admin/itinerarios/especies/" + idItinerario;
    }

    // POST /admin/itinerarios/especies/desvincular
    @PostMapping("/especies/desvincular")
    public String desvinculaEspecie(@RequestParam Integer idItinerario, @RequestParam Integer idEspecie, RedirectAttributes redirectAttributes) {
        try {
            itinerarioEspecieService.desvincularEspecieDeItinerario(idItinerario, idEspecie);
            // Actualizar numEspecies
            Integer cantidadEspecies = itinerarioEspecieService.contarEspeciesPorItinerario(idItinerario);
            Itinerario itin = itinerarioService.obtenerItinerarioPorId(idItinerario).get();
            itin.setNumEspecies(cantidadEspecies);
            itinerarioService.actualizarItinerario(idItinerario, itin);
            redirectAttributes.addFlashAttribute("success", "Especie desvinculada correctamente");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error al desvincular especie: " + ex.getMessage());
        }
        return "redirect:/admin/itinerarios/especies/" + idItinerario;
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