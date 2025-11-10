package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.dto.ZonaDto;
import com.grupo10.mundomarino.entity.*;
import com.grupo10.mundomarino.mapper.ZonaMapper;
import com.grupo10.mundomarino.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItinerarioZonaServiceImpl implements ItinerarioZonaService {

    private final ItinerarioRepository itinerarioRepository;
    private final ZonaRepository zonaRepository;
    private final ItinerarioZonaRepository itZonaRepository;

    public ItinerarioZonaServiceImpl(ItinerarioRepository itinerarioRepository,
            ZonaRepository zonaRepository,
            ItinerarioZonaRepository itZonaRepository) {
        this.itinerarioRepository = itinerarioRepository;
        this.zonaRepository = zonaRepository;
        this.itZonaRepository = itZonaRepository;
    }

    @Override
    public ItinerarioZona vincularZonaAItinerario(Integer idItinerario, Integer idZona) {
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        Zona zona = zonaRepository.findById(idZona)
                .orElseThrow(() -> new IllegalArgumentException("Zona no encontrada: " + idZona));
        // Evitar duplicados
        itZonaRepository.findByItinerarioAndZona(itin, zona)
                .ifPresent(existing -> {
                    throw new IllegalStateException("Zona ya incluida en el itinerario");
                });

        ItinerarioZona iz = ItinerarioZona.builder()
                .itinerario(itin)
                .zona(zona)
                .build();
        return itZonaRepository.save(iz);
    }

    @Override
    public void desvincularZonaDeItinerario(Integer idItinerario, Integer idZona) {
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        Zona zona = zonaRepository.findById(idZona)
                .orElseThrow(() -> new IllegalArgumentException("Zona no encontrada: " + idZona));
        itZonaRepository.findByItinerarioAndZona(itin, zona)
                .ifPresent(iz -> itZonaRepository.delete(iz));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Zona> obtenerZonasPorItinerario(Integer idItinerario) {
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        return itZonaRepository.findByItinerario(itin)
                .stream()
                .map(ItinerarioZona::getZona)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Itinerario> obtenerItinerariosPorZona(Integer idZona) {
        Zona zona = zonaRepository.findById(idZona)
                .orElseThrow(() -> new IllegalArgumentException("Zona no encontrada: " + idZona));
        return itZonaRepository.findByZona(zona)
                .stream()
                .map(ItinerarioZona::getItinerario)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaDto> obtenerZonasPorItinerarioDto(Integer idItinerario) {
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));
        return itZonaRepository.findByItinerario(itin)
                .stream()
                .map(iz -> ZonaMapper.toDto(iz.getZona()))
                .toList();
    }
}
