package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Habitat;
import com.grupo10.mundomarino.exception.ServiceException;
import com.grupo10.mundomarino.repository.HabitatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class HabitatServiceImpl implements HabitatService {

    private final HabitatRepository habitatRepository;

    public HabitatServiceImpl(HabitatRepository habitatRepository) {
        this.habitatRepository = habitatRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitat> obtenerTodosLosHabitats() {
        log.info("Obteniendo todos los habitats");
        return habitatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Habitat> obtenerHabitatPorId(Integer id) {
        log.info("Buscando habitat por id {}", id);
        return habitatRepository.findById(id);
    }

    @Override
    public Habitat crearHabitat(Habitat habitat) {
        log.info("Creando habitat: {}", habitat);
        return habitatRepository.save(habitat);
    }

    @Override
    public Habitat actualizarHabitat(Integer id, Habitat habitat) {
        log.info("Actualizando habitat id {}", id);
        Habitat existente = habitatRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Habitat no encontrado: " + id));
        existente.setNombre(habitat.getNombre());
        return habitatRepository.save(existente);
    }

    @Override
    public void eliminarHabitat(Integer id) {
        log.info("Eliminando habitat id {}", id);
        habitatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Habitat> buscarPorNombre(String nombre) {
        log.info("Buscando habitats por nombre: {}", nombre);
        return habitatRepository.findByNombreContainingIgnoreCase(nombre);
    }
}