package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Especie;
import com.grupo10.mundomarino.entity.Zona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    List<Especie> findByZona(Zona zona);
    List<Especie> findByNombreContainingIgnoreCase(String nombre);
}
