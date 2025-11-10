package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.ItinerarioZona;
import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ItinerarioZonaRepository extends JpaRepository<ItinerarioZona, Long> {
    List<ItinerarioZona> findByItinerario(Itinerario itinerario);
    List<ItinerarioZona> findByZona(Zona zona);
    Optional<ItinerarioZona> findByItinerarioAndZona(Itinerario itinerario, Zona zona);
    void deleteByItinerario(Itinerario itinerario);
    void deleteByZona(Zona zona);
}
