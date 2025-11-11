package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.ItinerarioZonaDto;
import com.grupo10.mundomarino.entity.ItinerarioZona;

public class ItinerarioZonaMapper {
    public static ItinerarioZonaDto toDto(ItinerarioZona iz) {
        if (iz == null) return null;
        return new ItinerarioZonaDto(
            iz.getId(),
            iz.getItinerario() != null ? iz.getItinerario().getIdItinerario() : null,
            iz.getZona() != null ? iz.getZona().getIdZona() : null
        );
    }
}
