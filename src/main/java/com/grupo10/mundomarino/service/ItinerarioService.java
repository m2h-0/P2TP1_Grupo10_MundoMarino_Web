package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Itinerario;
import java.util.List;
import java.util.Optional;

public interface ItinerarioService {
    List<Itinerario> obtenerTodosLosItinerarios();
    Optional<Itinerario> obtenerItinerarioPorId(Integer id);
    Itinerario crearItinerario(Itinerario itinerario);
    Itinerario actualizarItinerario(Integer id, Itinerario itinerario);
    void eliminarItinerario(Integer id);
    List<Itinerario> ordenarPorNumEspeciesDesc();
    void asignarGuia(Integer idItinerario, String idGuia);
}