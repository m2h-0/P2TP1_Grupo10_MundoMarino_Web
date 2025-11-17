package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.service.SistemaGestionService;
import com.grupo10.mundomarino.dto.ReservaDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST que expone la funcionalidad de los tres roles mediante HTTP.
 */
@RestController // Anotación clave para un controlador web
@RequestMapping("/gestion")
public class SistemaGestionController {

    // 1. Declaración e Inyección de la dependencia
    private final SistemaGestionService sistemaService;

    // Constructor: Spring inyecta la instancia de SistemaGestionService
    public SistemaGestionController(SistemaGestionService sistemaService) {
        this.sistemaService = sistemaService;
    }

    // =================================================================
    //                    ENDPOINT DEL ADMINISTRADOR
    // =================================================================
    
    // Endpoint para crear una reserva (Simula la línea: sistema.crearReserva(...))
    @PostMapping("/reservas")
    public ReservaDto crearReserva(@RequestBody ReservaDto reserva) {
        System.out.println("LOG: Solicitud de ADMIN/Venta: Crear Reserva.");
        return sistemaService.crearReserva(reserva);
    }
    
    // Endpoint para cancelar una reserva (Simula la línea: sistema.cancelarReserva(...))
    @DeleteMapping("/reservas/{id}")
    public String cancelarReserva(@PathVariable Long id) {
        if (sistemaService.cancelarReserva(id)) {
            return "❌ Reserva ID " + id + " cancelada exitosamente.";
        }
        return "⚠️ Error: Reserva ID " + id + " no encontrada.";
    }

    // =================================================================
    //                       ENDPOINT DEL GUÍA
    // =================================================================
    
    // Endpoint para obtener la agenda (Simula la línea: sistema.obtenerTodasLasReservas())
    @GetMapping("/reservas")
    public List<ReservaDto> obtenerTodasLasReservas() {
        return sistemaService.obtenerTodasLasReservas();
    }
    
    // Endpoint para consultar el itinerario de zonas (Simula la línea: sistema.mostrarZonasPorItinerario(...))
    @GetMapping("/itinerarios/{idItinerario}/zonas")
    public List<?> obtenerZonasPorItinerario(@PathVariable Long idItinerario) {
        return sistemaService.obtenerZonasPorItinerario(idItinerario);
    }
    
    // =================================================================
    //                      ENDPOINT DEL CUIDADOR
    // =================================================================
    
    // Endpoint para consultar el inventario (Simula la línea: sistema.obtenerInventario())
    @GetMapping("/inventario")
    public Map<String, Integer> obtenerInventario() {
        return sistemaService.obtenerInventario();
    }
    
    // Endpoint para registrar el uso (Simula la línea: sistema.registrarUsoSuministro(...))
    @PostMapping("/inventario/uso")
    public String registrarUso(@RequestParam String suministro, @RequestParam int cantidad) {
        sistemaService.registrarUsoSuministro(suministro, cantidad);
        return "Uso de " + cantidad + " de " + suministro + " registrado.";
    }
}