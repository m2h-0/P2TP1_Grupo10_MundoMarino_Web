package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.entity.Guia;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import com.grupo10.mundomarino.repository.ItinerarioRepository;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guia")
@PreAuthorize("hasRole('GUIA')")
public class GuiaController {

    private final EmpleadoRepository empleadoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ItinerarioRepository itinerarioRepo;

    public GuiaController(EmpleadoRepository empleadoRepo,
                        UsuarioRepository usuarioRepo,
                        ItinerarioRepository itinerarioRepo) {
        this.empleadoRepo = empleadoRepo;
        this.usuarioRepo = usuarioRepo;
        this.itinerarioRepo = itinerarioRepo;
    }

    @GetMapping
    public String guiaDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Guía");
        return "dashboard/guia";
    }

    @GetMapping("/itinerarios")
    public String verItinerarios(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("debugUser", username);

        Optional<Usuario> optUsuario = usuarioRepo.findByUsername(username);
        if (optUsuario.isEmpty()) {
            model.addAttribute("error", "Usuario no encontrado. Contacte al administrador.");
            model.addAttribute("itinerarios", List.of());
            return "guia/itinerarios";
        }

        Usuario usuario = optUsuario.get();
        Long usuarioId = usuario.getId();

        Optional<Empleado> optEmpleado = empleadoRepo.findByUsuarioId(usuarioId);
        if (optEmpleado.isEmpty() || !(optEmpleado.get() instanceof Guia)) {
            model.addAttribute("error", "No se encontró un perfil de guía vinculado a este usuario.");
            model.addAttribute("itinerarios", List.of());
            return "guia/itinerarios";
        }

        Guia guia = (Guia) optEmpleado.get();
        List<Itinerario> itinerarios = itinerarioRepo.findByGuia(guia);
        model.addAttribute("itinerarios", itinerarios);
        model.addAttribute("guia", guia);
        return "guia/itinerarios";
    }

    @GetMapping("/itinerarios/ver/{id}")
    public String verItinerario(@PathVariable(name = "id") Integer id, Model model) {
        Itinerario itinerario = itinerarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + id));
        model.addAttribute("itinerario", itinerario);
        return "guia/ver-itinerario";
    }

    public String guiaRecorridos(Model model) {
        model.addAttribute("titulo", "Recorridos");
        return "guia/recorridos";
    }
}
