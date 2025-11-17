
package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarioZonaDto {
    private Long id;
    private Long idItinerario; // Usamos Long para coincidir con la PK de Itinerario
    private Long idZona; // Usamos Long para coincidir con la PK de Zona
}