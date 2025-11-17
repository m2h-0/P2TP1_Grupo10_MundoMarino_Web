package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que mapea la tabla de 'reservas' en la Base de Datos.
 * (Rol Administrador/Guía)
 */
@Entity // Le dice a JPA que esta clase es una tabla
@Table(name = "reservas") // Opcional, nombre de la tabla en la BBDD
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEntity {

    @Id // Marca el campo como la Clave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID será auto-generado por la BBDD
    private Long id; // Cambiamos a Long, que es estándar para IDs de BBDD

    private String nombreCliente;
    private String fecha;
    private String hora;
    private int numVisitantes;
    
    // NOTA: No necesita el método toString si ya usa @Data de Lombok.
}