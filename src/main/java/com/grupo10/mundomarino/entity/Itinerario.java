package com.grupo10.mundomarino.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import com.grupo10.mundomarino.entity.Empleado;

@Entity
@Table(name = "itinerarios")
@Data
public class Itinerario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itinerario")
    private Integer idItinerario;
    
    @Column(name = "desc_recorrido", length = 500)
    private String descRecorrido;
    
    private Integer longitud;
    
    @Column(name = "max_visitantes")
    private Integer maxVisitantes;
    
    @Column(name = "num_especies")
    private Integer numEspecies;

    @ManyToOne
    @JoinColumn(name = "id_empleado_guia")
    private Empleado guia;
}