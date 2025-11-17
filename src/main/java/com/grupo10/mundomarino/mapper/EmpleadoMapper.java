package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.EmpleadoDto;
import com.grupo10.mundomarino.entity.Empleado;

public class EmpleadoMapper {
    public static EmpleadoDto toDto(Empleado e) {
        if (e == null) return null;
        return new EmpleadoDto(
            e.getIdEmpleado(),
            e.getNombre(),
            e.getDireccion(),
            e.getTelefono(),
            e.getFechaIngreso(),
            e.getTipo()
        );
    }
}
