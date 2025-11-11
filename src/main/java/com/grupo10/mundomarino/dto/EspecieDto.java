package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecieDto {
    private Integer idEspecie;
    private String nombre;
    private String nombreCientifico;
    private String descripcion;
    private Integer idZona;
}
