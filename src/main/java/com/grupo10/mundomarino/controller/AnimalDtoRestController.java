package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.dto.AnimalDto;
import com.grupo10.mundomarino.service.AnimalServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animales")
@CrossOrigin(origins = "*")
public class AnimalDtoRestController {

    private final AnimalServiceDto service;

    public AnimalDtoRestController(AnimalServiceDto service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnimalDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> porId(@PathVariable Integer id) {
        AnimalDto dto = service.porId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AnimalDto> crear(@RequestBody AnimalDto dto) {
        AnimalDto creado = service.guardarDesdeDto(dto);
        return ResponseEntity.status(201).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> actualizar(@PathVariable Integer id, @RequestBody AnimalDto dto) {
        dto.setIdAnimal(id);
        AnimalDto actualizado = service.guardarDesdeDto(dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        service.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
