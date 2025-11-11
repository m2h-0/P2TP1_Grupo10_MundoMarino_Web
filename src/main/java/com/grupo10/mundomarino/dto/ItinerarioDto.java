package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarioDto {
    private Integer idItinerario;
    private String descRecorrido;
    private Integer longitud;
    private Integer maxVisitantes;
    private Integer numEspecies;
}
