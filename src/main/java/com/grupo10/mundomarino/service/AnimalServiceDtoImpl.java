package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.dto.AnimalDto;
import com.grupo10.mundomarino.entity.Animal;
import com.grupo10.mundomarino.mapper.AnimalMapper;
import com.grupo10.mundomarino.repository.AnimalRepository;
import com.grupo10.mundomarino.repository.EspecieRepository;
import com.grupo10.mundomarino.repository.HabitatRepository;
import com.grupo10.mundomarino.repository.ZonaRepository;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnimalServiceDtoImpl implements AnimalServiceDto {

    private final AnimalRepository repo;
    private final EspecieRepository especieRepo;
    private final HabitatRepository habitatRepo;
    private final ZonaRepository zonaRepo;
    private final EmpleadoRepository empleadoRepo;

    public AnimalServiceDtoImpl(AnimalRepository repo,
            EspecieRepository especieRepo,
            HabitatRepository habitatRepo,
            ZonaRepository zonaRepo,
            EmpleadoRepository empleadoRepo) {
        this.repo = repo;
        this.especieRepo = especieRepo;
        this.habitatRepo = habitatRepo;
        this.zonaRepo = zonaRepo;
        this.empleadoRepo = empleadoRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listar() {
        return repo.findAll().stream().map(AnimalMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalDto porId(Integer id) {
        return repo.findById(id).map(AnimalMapper::toDto).orElse(null);
    }

    @Override
    public AnimalDto guardarDesdeDto(AnimalDto dto) {
        final Animal animal = obtenerOCrearAnimal(dto);

        animal.setNombre(dto.getNombre());
        animal.setDescripcion(dto.getDescripcion());

        if (dto.getIdEspecie() != null) {
            especieRepo.findById(dto.getIdEspecie()).ifPresent(animal::setEspecie);
        }
        if (dto.getIdHabitat() != null) {
            habitatRepo.findById(dto.getIdHabitat()).ifPresent(animal::setHabitat);
        }
        if (dto.getIdZona() != null) {
            zonaRepo.findById(dto.getIdZona()).ifPresent(animal::setZona);
        }
        if (dto.getIdCuidador() != null) {
            empleadoRepo.findById(dto.getIdCuidador()).ifPresent(emp -> animal.setCuidador((com.grupo10.mundomarino.entity.Cuidador) emp));
        }

        Animal saved = repo.save(animal);
        return AnimalMapper.toDto(saved);
    }

    private Animal obtenerOCrearAnimal(AnimalDto dto) {
        if (dto.getIdAnimal() != null) {
            return repo.findById(dto.getIdAnimal()).orElse(new Animal());
        }
        return new Animal();
    }

    @Override
    public void borrar(Integer id) {
        repo.deleteById(id);
    }
}
