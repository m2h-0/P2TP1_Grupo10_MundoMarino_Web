package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.exception.ServiceException;
import com.grupo10.mundomarino.repository.ItinerarioRepository;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import com.grupo10.mundomarino.entity.Empleado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ItinerarioServiceImpl implements ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;
    private final EmpleadoRepository empleadoRepository;

    public ItinerarioServiceImpl(ItinerarioRepository itinerarioRepository, EmpleadoRepository empleadoRepository) {
        this.itinerarioRepository = itinerarioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Itinerario> obtenerTodosLosItinerarios() {
        log.info("Obteniendo todos los itinerarios");
        return itinerarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Itinerario> obtenerItinerarioPorId(Integer id) {
        log.info("Buscando itinerario por id {}", id);
        return itinerarioRepository.findById(id);
    }

    @Override
    public Itinerario crearItinerario(Itinerario itinerario) {
        log.info("Creando itinerario: {}", itinerario);
        return itinerarioRepository.save(itinerario);
    }

    @Override
    public Itinerario actualizarItinerario(Integer id, Itinerario itinerario) {
        log.info("Actualizando itinerario id {}", id);
        Itinerario existente = itinerarioRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Itinerario no encontrado: " + id));
        existente.setDescRecorrido(itinerario.getDescRecorrido());
        existente.setLongitud(itinerario.getLongitud());
        existente.setMaxVisitantes(itinerario.getMaxVisitantes());
        existente.setNumEspecies(itinerario.getNumEspecies());
        return itinerarioRepository.save(existente);
    }

    @Override
    public void eliminarItinerario(Integer id) {
        log.info("Eliminando itinerario id {}", id);
        itinerarioRepository.deleteById(id);
    }

    @Override
    public void asignarGuia(Integer idItinerario, String idGuia) {
        log.info("Asignando guia {} al itinerario {}", idGuia, idItinerario);
        Itinerario itin = itinerarioRepository.findById(idItinerario)
                .orElseThrow(() -> new IllegalArgumentException("Itinerario no encontrado: " + idItinerario));

        Empleado guia = empleadoRepository.findById(idGuia)
                .orElseThrow(() -> new IllegalArgumentException("Guia no encontrado: " + idGuia));

        if (guia.getTipo() == null || !guia.getTipo().equalsIgnoreCase("GUIA")) {
            throw new IllegalArgumentException("El empleado seleccionado no es un guía");
        }

        itin.setGuia(guia);
        itinerarioRepository.save(itin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Itinerario> ordenarPorNumEspeciesDesc() {
        log.info("Obteniendo itinerarios ordenados por numero de especies desc");
        return itinerarioRepository.findByOrderByNumEspeciesDesc();
    }
}