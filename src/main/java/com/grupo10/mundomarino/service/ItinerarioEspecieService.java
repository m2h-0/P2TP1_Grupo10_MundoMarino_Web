package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.ItinerarioEspecie;

import java.util.List;

public interface ItinerarioEspecieService {
    ItinerarioEspecie vincularEspecieAItinerario(Integer idItinerario, Integer idEspecie);
    void desvincularEspecieDeItinerario(Integer idItinerario, Integer idEspecie);
    List<Especie> obtenerEspeciesPorItinerario(Integer idItinerario);
    List<Itinerario> obtenerItinerariosPorEspecie(Integer idEspecie);
    Integer contarEspeciesPorItinerario(Integer idItinerario);
}
