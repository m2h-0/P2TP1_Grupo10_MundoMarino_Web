package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.service.SistemaGestionService;
import com.grupo10.mundomarino.dto.ReservaDto;
import java.util.List;

/**
 * Capa de Controlador (Simulación Main).
 */
public class SistemaGestionController {
    public static void main(String[] args) {
        // Inicializamos la capa de Servicio
        SistemaGestionService sistema = new SistemaGestionService();
        System.out.println("--- Sistema de Gestión de Mundo Marino iniciado ---");

        // --- SIMULACIÓN ---
        
        // 1. Tareas del ADMINISTRADOR (Creación de agenda)
        System.out.println("\n*** ADMINISTRADOR: Carga de Agenda ***");
        sistema.crearReserva("Juan Pérez", "18/11", "10:00", 4); 
        sistema.crearReserva("Ana Gómez", "18/11", "14:30", 2);  
        
        // 2. Tareas del CUIDADOR (Mantenimiento matutino)
        System.out.println("\n*** CUIDADOR: Mantenimiento e Inventario ***");
        sistema.registrarUsoSuministro("Comida Animal", 10);
        sistema.registrarUsoSuministro("Suministros Limpieza", 5);

        // 3. Tareas del GUÍA (Consulta del tour y zonas)
        System.out.println("\n*** GUÍA: Preparación del Tour 101 ***");
        sistema.mostrarZonasPorItinerario(101); // El guía consulta por dónde ir
        
        List<ReservaDto> agenda = sistema.obtenerTodasLasReservas();
        System.out.println("\n[GUÍA] Agenda de Hoy:");
        agenda.forEach(r -> System.out.println(r.toString()));

        // 4. Tareas del ADMINISTRADOR (Gestión final)
        System.out.println("\n*** ADMINISTRADOR: Cierre de Agenda ***");
        if (sistema.cancelarReserva(1)) { // Juan Pérez cancela
            System.out.println(" ADMIN: Reserva ID 1 cancelada exitosamente.");
        }
        
        // 5. Reporte Final (CUIDADOR/ADMINISTRADOR)
        System.out.println("\n--- REPORTE FINAL DE INVENTARIO ---");
        sistema.obtenerInventario().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}