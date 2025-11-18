package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Itinerario;
import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.ItinerarioEspecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItinerarioEspecieRepository extends JpaRepository<ItinerarioEspecie, Long> {
    List<ItinerarioEspecie> findByItinerario(Itinerario itinerario);
    List<ItinerarioEspecie> findByEspecie(Especie especie);
    Optional<ItinerarioEspecie> findByItinerarioAndEspecie(Itinerario itinerario, Especie especie);
}
