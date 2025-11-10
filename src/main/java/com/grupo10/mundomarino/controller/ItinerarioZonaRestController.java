package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.dto.ZonaDto;
import com.grupo10.mundomarino.entity.ItinerarioZona;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.Zona;
import com.grupo10.mundomarino.service.ItinerarioZonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/itinerario-zona")
@CrossOrigin(origins = "*")
public class ItinerarioZonaRestController {

    private final ItinerarioZonaService service;

    public ItinerarioZonaRestController(ItinerarioZonaService service) {
        this.service = service;
    }

    @PostMapping("/vincular")
    public ResponseEntity<ItinerarioZona> vincular(@RequestParam Integer idItinerario,
            @RequestParam Integer idZona) {
        ItinerarioZona created = service.vincularZonaAItinerario(idItinerario, idZona);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/desvincular")
    public ResponseEntity<Void> desvincular(@RequestParam Integer idItinerario,
            @RequestParam Integer idZona) {
        service.desvincularZonaDeItinerario(idItinerario, idZona);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/zonas-por-itinerario/{idItinerario}")
    public ResponseEntity<List<Zona>> zonasPorItinerario(@PathVariable Integer idItinerario) {
        return ResponseEntity.ok(service.obtenerZonasPorItinerario(idItinerario));
    }

    @GetMapping("/itinerarios-por-zona/{idZona}")
    public ResponseEntity<List<Itinerario>> itinerariosPorZona(@PathVariable Integer idZona) {
        return ResponseEntity.ok(service.obtenerItinerariosPorZona(idZona));
    }

    @GetMapping("/zonas-por-itinerario-dto/{idItinerario}")
    public ResponseEntity<List<ZonaDto>> zonasPorItinerarioDto(@PathVariable Integer idItinerario) {
        return ResponseEntity.ok(service.obtenerZonasPorItinerarioDto(idItinerario));
    }
}
