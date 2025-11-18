package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.ItinerarioEspecie;
import com.grupo10.mundomarino.repository.EspecieRepository;
import com.grupo10.mundomarino.repository.ItinerarioRepository;
import com.grupo10.mundomarino.repository.ItinerarioEspecieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class ItinerarioEspecieServiceImpl implements ItinerarioEspecieService {

    private final ItinerarioRepository itinerarioRepository;
    private final EspecieRepository especieRepository;
    private final ItinerarioEspecieRepository itEspecieRepository;

    public ItinerarioEspecieServiceImpl(ItinerarioRepository itinerarioRepository,
                                       EspecieRepository especieRepository,
                                       ItinerarioEspecieRepository itEspecieRepository) {
        this.itinerarioRepository = itinerarioRepository;
        this.especieRepository = especieRepository;
        this.itEspecieRepository = itEspecieRepository;
    }

    @Override
    public ItinerarioEspecie vincularEspecieAItinerario(Integer idItinerario, Integer idEspecie) {
        log.info("Vinculando especie {} al itinerario {}", idEspecie, idItinerario);
        
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada: " + idEspecie));
        
        // Evitar duplicados
        itEspecieRepository.findByItinerarioAndEspecie(itin, especie)
                .ifPresent(existing -> {
                    throw new IllegalStateException("Especie ya incluida en el itinerario");
                });

        ItinerarioEspecie ie = ItinerarioEspecie.builder()
                .itinerario(itin)
                .especie(especie)
                .build();
        return itEspecieRepository.save(ie);
    }

    @Override
    public void desvincularEspecieDeItinerario(Integer idItinerario, Integer idEspecie) {
        log.info("Desvinculando especie {} del itinerario {}", idEspecie, idItinerario);
        
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada: " + idEspecie));
        
        itEspecieRepository.findByItinerarioAndEspecie(itin, especie)
                .ifPresent(ie -> itEspecieRepository.delete(ie));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Especie> obtenerEspeciesPorItinerario(Integer idItinerario) {
        log.info("Obteniendo especies para itinerario {}", idItinerario);
        
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        
        return itEspecieRepository.findByItinerario(itin)
                .stream()
                .map(ItinerarioEspecie::getEspecie)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Itinerario> obtenerItinerariosPorEspecie(Integer idEspecie) {
        log.info("Obteniendo itinerarios para especie {}", idEspecie);
        
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new IllegalArgumentException("Especie no encontrada: " + idEspecie));
        
        return itEspecieRepository.findByEspecie(especie)
                .stream()
                .map(ItinerarioEspecie::getItinerario)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarEspeciesPorItinerario(Integer idItinerario) {
        log.info("Contando especies para itinerario {}", idItinerario);
        
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        
        return itEspecieRepository.findByItinerario(itin).size();
    }
}
