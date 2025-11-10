package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Empleado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    // filtrar por tipo: ADMIN, GUIA, CUIDADOR
    List<Empleado> findByTipo(String tipo); 
}