package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Empleado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    List<Empleado> findByTipo(String tipo); // ADMIN, GUIA, CUIDADOR
}