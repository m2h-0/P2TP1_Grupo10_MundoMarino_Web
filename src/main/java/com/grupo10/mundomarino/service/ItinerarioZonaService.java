package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.dto.ZonaDto;
import com.grupo10.mundomarino.entity.ItinerarioZona;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.Zona;
import java.util.List;

public interface ItinerarioZonaService {
    ItinerarioZona vincularZonaAItinerario(Integer idItinerario, Integer idZona);
    void desvincularZonaDeItinerario(Integer idItinerario, Integer idZona);
    List<Zona> obtenerZonasPorItinerario(Integer idItinerario);
    List<Itinerario> obtenerItinerariosPorZona(Integer idZona);
    List<ZonaDto> obtenerZonasPorItinerarioDto(Integer idItinerario);
}
