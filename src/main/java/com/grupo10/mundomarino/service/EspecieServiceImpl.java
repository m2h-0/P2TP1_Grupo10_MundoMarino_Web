package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Zona;
import com.grupo10.mundomarino.exception.EspecieNotFoundException;
import com.grupo10.mundomarino.repository.EspecieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class EspecieServiceImpl implements EspecieService {

    private final EspecieRepository especieRepository;

    public EspecieServiceImpl(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especie> obtenerTodasLasEspecies() {
        log.info("Obteniendo todas las especies");
        return especieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Especie> obtenerEspeciePorId(Integer id) {
        log.info("Buscando especie por id {}", id);
        return especieRepository.findById(id);
    }

    @Override
    public Especie crearEspecie(Especie especie) {
        log.info("Creando especie: {}", especie);
        return especieRepository.save(especie);
    }

    @Override
    public Especie actualizarEspecie(Integer id, Especie especie) {
        log.info("Actualizando especie id {}", id);
        Especie existente = especieRepository.findById(id)
                .orElseThrow(() -> new EspecieNotFoundException("Especie no encontrada: " + id));
        // actualizar campos relevantes
        existente.setNombre(especie.getNombre());
        existente.setNombreCientifico(especie.getNombreCientifico());
        existente.setDescripcion(especie.getDescripcion());
        existente.setZona(especie.getZona());
        return especieRepository.save(existente);
    }

    @Override
    public void eliminarEspecie(Integer id) {
        log.info("Eliminando especie id {}", id);
        especieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especie> buscarPorNombre(String nombre) {
        log.info("Buscando especies por nombre: {}", nombre);
        return especieRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especie> obtenerPorZona(Zona zona) {
        log.info("Obteniendo especies de zona: {}", zona);
        return especieRepository.findByZona(zona);
    }
}