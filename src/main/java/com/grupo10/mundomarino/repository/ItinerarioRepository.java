package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Itinerario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItinerarioRepository extends JpaRepository<Itinerario, Integer> {
    List<Itinerario> findByOrderByNumEspeciesDesc();
}
