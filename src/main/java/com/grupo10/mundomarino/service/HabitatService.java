package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Habitat;
import java.util.List;
import java.util.Optional;

public interface HabitatService {
    List<Habitat> obtenerTodosLosHabitats();
    Optional<Habitat> obtenerHabitatPorId(Integer id);
    Habitat crearHabitat(Habitat habitat);
    Habitat actualizarHabitat(Integer id, Habitat habitat);
    void eliminarHabitat(Integer id);
    List<Habitat> buscarPorNombre(String nombre);
}