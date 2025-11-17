package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    // Spring crea automáticamente métodos como save, findById, findAll, etc.
}