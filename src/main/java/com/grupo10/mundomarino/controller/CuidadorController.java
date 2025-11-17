package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Animal;
import com.grupo10.mundomarino.entity.Cuidador;
import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.service.AnimalService;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/cuidador")
@PreAuthorize("hasRole('CUIDADOR')")
public class CuidadorController {

    private final AnimalService animalService;
    private final EmpleadoRepository empleadoRepo;
    private final UsuarioRepository usuarioRepo;

    // constructor explícito para inyección
    public CuidadorController(AnimalService animalService,
                              EmpleadoRepository empleadoRepo,
                              UsuarioRepository usuarioRepo) {
        this.animalService = animalService;
        this.empleadoRepo = empleadoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/animales")
    public String verAnimales(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("debugUser", username);

        Optional<Usuario> optUsuario = usuarioRepo.findByUsername(username);
        if (optUsuario.isEmpty()) {
            model.addAttribute("error", "Usuario no encontrado. Contacte al administrador.");
            model.addAttribute("animales", List.of());
            return "cuidador/animales";
        }

        Usuario usuario = optUsuario.get();
        Long usuarioId = usuario.getId();

        Optional<Empleado> optEmpleado = empleadoRepo.findByUsuarioId(usuarioId);
        if (optEmpleado.isEmpty() || !(optEmpleado.get() instanceof Cuidador)) {
            model.addAttribute("error", "No se encontró un perfil de cuidador vinculado a este usuario.");
            model.addAttribute("animales", List.of());
            return "cuidador/animales";
        }

        Cuidador cuidador = (Cuidador) optEmpleado.get();
        List<Animal> animales = animalService.listarPorCuidadorId(cuidador.getIdEmpleado());
        model.addAttribute("animales", animales);
        model.addAttribute("cuidador", cuidador);
        return "cuidador/animales";
    }


    @GetMapping
    public String cuidadorDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Cuidador");
        return "dashboard/cuidador";
    }

    @GetMapping("/animales/ver/{id}")
    public String verFichaAnimal(@PathVariable Integer id, Model model) {
        Animal animal = animalService.porId(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + id));
        model.addAttribute("animal", animal);
        return "cuidador/ver-ficha";
    }
}
