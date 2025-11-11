package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.ItinerarioDto;
import com.grupo10.mundomarino.entity.Itinerario;

public class ItinerarioMapper {
    public static ItinerarioDto toDto(Itinerario i) {
        if (i == null) return null;
        return new ItinerarioDto(
            i.getIdItinerario(),
            i.getDescRecorrido(),
            i.getLongitud(),
            i.getMaxVisitantes(),
            i.getNumEspecies()
        );
    }
}
