package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.dto.AnimalDto;
import java.util.List;

public interface AnimalServiceDto {
    List<AnimalDto> listar();
    AnimalDto porId(Integer id);
    AnimalDto guardarDesdeDto(AnimalDto dto);
    void borrar(Integer id);
}
