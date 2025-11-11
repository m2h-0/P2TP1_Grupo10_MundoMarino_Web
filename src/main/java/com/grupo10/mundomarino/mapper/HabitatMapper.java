package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.HabitatDto;
import com.grupo10.mundomarino.entity.Habitat;

public class HabitatMapper {
    public static HabitatDto toDto(Habitat h) {
        if (h == null) return null;
        return new HabitatDto(h.getIdHabitat(), h.getNombre());
    }
}
