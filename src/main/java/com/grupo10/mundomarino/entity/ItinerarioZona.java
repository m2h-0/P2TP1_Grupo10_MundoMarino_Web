package com.grupo10.mundomarino.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_itinerario", nullable = false)
    private Itinerario itinerario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    // Si necesitas atributos extra (orden en el recorrido, tiempo estimado, etc.)
    // puedes agregarlos aquí, por ejemplo:
    // private Integer orden;
}
