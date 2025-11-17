package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.EspecieDto;
import com.grupo10.mundomarino.entity.Especie;

public class EspecieMapper {
    public static EspecieDto toDto(Especie e) {
        if (e == null) return null;
        return new EspecieDto(
            e.getIdEspecie(),
            e.getNombre(),
            e.getNombreCientifico(),
            e.getDescripcion(),
            e.getZona() != null ? e.getZona().getIdZona() : null
        );
    }
}
