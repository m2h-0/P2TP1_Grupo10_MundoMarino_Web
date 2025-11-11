package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Habitat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitatRepository extends JpaRepository<Habitat, Integer> {
    List<Habitat> findByNombreContainingIgnoreCase(String nombre);
}