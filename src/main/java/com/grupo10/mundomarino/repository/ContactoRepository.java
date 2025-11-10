package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

    // Buscar por DNI
    Optional<Contacto> findById(Integer id);

    // Buscar por correo
    Optional<Contacto> findByCorreo(String correo);

    // Verificar si existe un DNI
    boolean existsByDni(Integer dni);

    // Buscar contactos por apellido (búsqueda parcial, case insensitive)
    @Query("SELECT c FROM Contacto c WHERE LOWER(c.apellidos) LIKE LOWER(CONCAT(:apellido, '%'))")
    List<Contacto> findByApellidosStartingWithIgnoreCase(@Param("apellido") String apellido);

    // Buscar contactos por DNI que comience con ciertos dígitos
    @Query("SELECT c FROM Contacto c WHERE CAST(c.dni AS string) LIKE CONCAT(:dniPrefix, '%')")
    List<Contacto> findByDniStartingWith(@Param("dniPrefix") String dniPrefix);

    // Buscar por apellido o DNI (búsqueda combinada)
    @Query("SELECT c FROM Contacto c WHERE " +
           "LOWER(c.apellidos) LIKE LOWER(CONCAT(:criterio, '%')) OR " +
           "CAST(c.dni AS string) LIKE CONCAT(:criterio, '%')")
    List<Contacto> buscarPorApellidoODni(@Param("criterio") String criterio);
}