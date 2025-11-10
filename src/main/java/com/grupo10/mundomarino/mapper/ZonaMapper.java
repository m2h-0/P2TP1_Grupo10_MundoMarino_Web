package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.ZonaDto;
import com.grupo10.mundomarino.entity.Zona;

public class ZonaMapper {

    public static ZonaDto toDto(Zona zona) {
        if (zona == null) return null;
        return new ZonaDto(zona.getIdZona(), zona.getNombre());
    }
}
