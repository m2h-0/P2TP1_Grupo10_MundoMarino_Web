package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Zona;

import java.util.List;
import java.util.Optional;

public interface EspecieService {

    List<Especie> obtenerTodasLasEspecies();

    Optional<Especie> obtenerEspeciePorId(Integer id);

    Especie crearEspecie(Especie especie);

    Especie actualizarEspecie(Integer id, Especie especie);

    void eliminarEspecie(Integer id);

    List<Especie> buscarPorNombre(String nombre);

    List<Especie> obtenerPorZona(Zona zona);
}