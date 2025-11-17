package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.dto.ReservaDto;
import com.grupo10.mundomarino.entity.ReservaEntity;
import com.grupo10.mundomarino.repository.ReservaRepository;
import com.grupo10.mundomarino.repository.ItinerarioZonaRepository; // Para el Guía
import com.grupo10.mundomarino.entity.ItinerarioZona; // Para el Guía

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Servicio central que implementa la lógica de los roles: Administrador, Guía y Cuidador.
 */
@Service
public class SistemaGestionService {
    
    // Inyección de Repositorios (la conexión real a la BBDD)
    private final ReservaRepository reservaRepository;
    private final ItinerarioZonaRepository itinerarioZonaRepository;

    // Simulación simple de inventario que aún no está en BBDD
    private final Map<String, Integer> inventarioDb = new HashMap<>(); 

    // Constructor: Spring inyecta automáticamente las dependencias
    public SistemaGestionService(ReservaRepository reservaRepository, 
                                 ItinerarioZonaRepository itinerarioZonaRepository) {
        this.reservaRepository = reservaRepository;
        this.itinerarioZonaRepository = itinerarioZonaRepository;
        
        // Inicialización del inventario (Debería ir en una tabla real también)
        inventarioDb.put("Comida Animal", 50);
        inventarioDb.put("Suministros Limpieza", 20);
    }
    
    // =================================================================
    //               Lógica del ADMINISTRADOR (Persistencia)
    // =================================================================
    
    public ReservaDto crearReserva(ReservaDto dto) {
        // Mapeo DTO a Entity
        ReservaEntity entity = new ReservaEntity();
        entity.setNombreCliente(dto.getNombreCliente());
        entity.setFecha(dto.getFecha());
        entity.setHora(dto.getHora());
        entity.setNumVisitantes(dto.getNumVisitantes());

        // Guardar en la BBDD
        ReservaEntity savedEntity = reservaRepository.save(entity);
        
        // Devolver el DTO con el ID generado por la BBDD
        dto.setId(savedEntity.getId());
        return dto;
    }

    public boolean cancelarReserva(Long idReserva) {
        if (reservaRepository.existsById(idReserva)) {
            reservaRepository.deleteById(idReserva);
            return true;
        }
        return false;
    }
    
    // =================================================================
    //                   Lógica del GUÍA (Consulta)
    // =================================================================

    public List<ReservaDto> obtenerTodasLasReservas() {
        // Traer todas las Entidades de la BBDD y mapearlas a DTOs
        return reservaRepository.findAll().stream().map(this::mapEntityToDto)
                                 .collect(Collectors.toList());
    }
    
    public List<ItinerarioZona> obtenerZonasPorItinerario(Long idItinerario) {
        // Usa el método del Repository para consultar la BBDD
        return itinerarioZonaRepository.findByItinerarioId(idItinerario); 
    }
    
    // =================================================================
    //                 Lógica del CUIDADOR (Inventario Simulado)
    // =================================================================

    public void registrarUsoSuministro(String suministro, int cantidadUsada) {
        // Lógica de inventario... (debería usar un Repository en el futuro)
        if (inventarioDb.containsKey(suministro)) {
            int stockActual = inventarioDb.get(suministro);
            if (stockActual >= cantidadUsada) {
                inventarioDb.put(suministro, stockActual - cantidadUsada);
                System.out.println("CUIDADOR: Uso registrado. Stock restante de " + suministro + ": " + inventarioDb.get(suministro));
            } else {
                System.out.println(" CUIDADOR ALERTA: Stock insuficiente.");
            }
        }
    }

    // =================================================================
    //                   Mapeo (Helpers)
    // =================================================================

    private ReservaDto mapEntityToDto(ReservaEntity entity) {
        // Método helper para mapear Entidad a DTO
        return new ReservaDto(
            entity.getId(), 
            entity.getNombreCliente(), 
            entity.getFecha(), 
            entity.getHora(), 
            entity.getNumVisitantes()
        );
    }
}