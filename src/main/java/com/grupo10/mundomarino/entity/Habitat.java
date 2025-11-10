package com.grupo10.mundomarino.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "habitats")
@Data
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitat")
    private Integer idHabitat;
    
    @Column(nullable = false, length = 100)
    private String nombre;
}
