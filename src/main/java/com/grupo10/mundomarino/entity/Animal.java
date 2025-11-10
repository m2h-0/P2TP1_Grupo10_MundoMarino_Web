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

@Entity
@Table(name = "animales")
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Integer idAnimal;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "id_especie")
    private Especie especie;
    
    @ManyToOne
    @JoinColumn(name = "id_habitat")
    private Habitat habitat;
    
    @ManyToOne
    @JoinColumn(name = "id_zona")
    private Zona zona;
    
    @ManyToOne
    @JoinColumn(name = "id_cuidador")
    private Cuidador cuidador;
}
