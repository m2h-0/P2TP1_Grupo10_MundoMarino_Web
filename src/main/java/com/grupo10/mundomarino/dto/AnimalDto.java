package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    private Integer idAnimal;
    private String nombre;
    private String descripcion;
    private Integer idEspecie;
    private Integer idHabitat;
    private Integer idZona;
    private String idCuidador; // id del cuidador (idEmpleado)
}

