package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarioZonaDto {
    private Long id;
    private Integer idItinerario;
    private Integer idZona;
}
