package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.AnimalDto;
import com.grupo10.mundomarino.entity.Animal;

public class AnimalMapper {
    public static AnimalDto toDto(Animal a) {
        if (a == null) return null;
        return new AnimalDto(
            a.getIdAnimal(),
            a.getNombre(),
            a.getDescripcion(),
            a.getEspecie() != null ? a.getEspecie().getIdEspecie() : null,
            a.getHabitat() != null ? a.getHabitat().getIdHabitat() : null,
            a.getZona() != null ? a.getZona().getIdZona() : null,
            a.getCuidador() != null ? a.getCuidador().getIdEmpleado() : null
        );
    }
}

