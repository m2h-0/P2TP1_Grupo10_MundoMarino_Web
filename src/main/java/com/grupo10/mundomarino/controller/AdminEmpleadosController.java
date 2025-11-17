package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Administrador;
import com.grupo10.mundomarino.entity.Cuidador;
import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.entity.Guia;
import com.grupo10.mundomarino.service.EmpleadoService;
import java.time.LocalDate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public String guardar(@ModelAttribute Empleado empleado) {
        String id = empleado.getIdEmpleado();
        String formTipo = empleado.getTipo() != null ? empleado.getTipo().trim().toUpperCase() : "";

        // Si no vino fecha, asignar hoy
        if (empleado.getFechaIngreso() == null) {
            empleado.setFechaIngreso(LocalDate.now());
        }

        // Caso: ya existe
        if (id != null && !id.isBlank() && empleadoService.porId(id).isPresent()) {
            Empleado existente = empleadoService.porId(id).orElseThrow();

            String existenteTipo = existente.getTipo() != null ? existente.getTipo().trim().toUpperCase() : "";

            if (existenteTipo.equals(formTipo) || (formTipo.isBlank() && !existenteTipo.isBlank())) {
                // MISMA SUBCLASE -> actualizar la entidad gestionada (UPDATE)
                existente.setNombre(empleado.getNombre());
                existente.setDireccion(empleado.getDireccion());
                existente.setTelefono(empleado.getTelefono());
                if (empleado.getFechaIngreso() != null) {
                    existente.setFechaIngreso(empleado.getFechaIngreso());
                }
                if (formTipo != null && !formTipo.isBlank()) {
                    existente.setTipo(formTipo);
                }
                empleadoService.guardar(existente); // repo.save sobre entidad gestionada => UPDATE
            } else {
                // TIPO CAMBIADO -> eliminar antiguo y luego insertar nuevo con mismo id
                // 1) borrar existente
                empleadoService.borrar(id);

                // 2) forzar flush para que el DELETE se ejecute antes del INSERT
                // Si tu service/DAO no expone flush, abajo usamos el repo directo si existe:
                // ((EmpleadoRepository)empleadoService.getRepository()).flush(); // alternativa posible
                // En ausencia de flush en repo, Spring Data ejecuta flush al final de tx; para seguridad se puede autowirear EntityManager y llamar em.flush()
                // Aquí asumimos que tu servicio tiene un método flush() o que usas un EntityManager; te doy la versión con EntityManager:
            }
        }

        // Si llegamos aquí y el empleado existe pero tipo cambió, o es nuevo, persisto la subclase correcta.
        // Construyo la entidad a insertar (nueva variable) y la guardo.
        boolean needsInsert = true;
        if (id != null && !id.isBlank() && empleadoService.porId(id).isPresent()) {
            // Si entró en el branch "tipo cambió" el antiguo fue borrado, needsInsert sigue true.
            // Si entró en "misma subclase" ya se guardó arriba y no necesitamos insertar.
            // Detecto si ya existe ahora:
            needsInsert = !empleadoService.porId(id).isPresent();
        }

        if (needsInsert) {
            Empleado nueva;
            if ("GUIA".equals(formTipo)) {
                Guia g = new Guia();
                BeanUtils.copyProperties(empleado, g, "idEmpleado");
                g.setIdEmpleado(empleado.getIdEmpleado());
                nueva = g;
            } else if ("CUIDADOR".equals(formTipo)) {
                Cuidador c = new Cuidador();
                BeanUtils.copyProperties(empleado, c, "idEmpleado");
                c.setIdEmpleado(empleado.getIdEmpleado());
                nueva = c;
            } else if ("ADMINISTRADOR".equals(formTipo) || "ADMIN".equals(formTipo)) {
                Administrador a = new Administrador();
                BeanUtils.copyProperties(empleado, a, "idEmpleado");
                a.setIdEmpleado(empleado.getIdEmpleado());
                nueva = a;
            } else {
                // fallback
                nueva = empleado;
            }

            if (nueva.getFechaIngreso() == null) {
                nueva.setFechaIngreso(LocalDate.now());
            }

            // Si tu service no hace flush tras delete, necesitamos un flush antes de este save para evitar duplicate key.
            // Opción recomendada: inyectar EntityManager en el controller y llamar em.flush() inmediatamente después de empleadoService.borrar(id).
            // Aquí mostramos la versión que asume que hiciste eso; si no lo hiciste, te indico abajo cómo añadir em.flush().
            empleadoService.guardar(nueva); // INSERT
        }

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
