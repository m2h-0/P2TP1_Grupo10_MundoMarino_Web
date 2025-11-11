package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDto {
    private String idEmpleado;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDate fechaIngreso;
    private String tipo; // GUIA, CUIDADOR, ADMINISTRADOR
}
