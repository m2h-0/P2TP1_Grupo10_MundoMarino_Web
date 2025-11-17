package com.grupo10.mundomarino.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * Entidad para la relación Muchos a Muchos (Itinerario <-> Zona).
 * Mapea la tabla 'itinerario_zona'.
 */
@Entity
@Table(name = "itinerario_zona",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_itinerario", "id_zona"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItinerarioZona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itinerario_zona")
    private Long id;

    // Relación ManyToOne a la tabla Itinerario
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_itinerario", nullable = false)
    private Itinerario itinerario;

    // Relación ManyToOne a la tabla Zona
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;
}