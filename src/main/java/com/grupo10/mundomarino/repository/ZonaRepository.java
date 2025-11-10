package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Zona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
    Optional<Zona> findByNombre(String nombre);
}
