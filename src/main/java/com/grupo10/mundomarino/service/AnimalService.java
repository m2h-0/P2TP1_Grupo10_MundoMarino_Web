package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Animal;
import com.grupo10.mundomarino.entity.Cuidador;
import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.repository.AnimalRepository;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnimalService {

    private final AnimalRepository animalRepo;
    private final EmpleadoRepository empleadoRepo;

    public AnimalService(AnimalRepository animalRepo, EmpleadoRepository empleadoRepo) {
        this.animalRepo = animalRepo;
        this.empleadoRepo = empleadoRepo;
    }

    @Transactional(readOnly = true)
    public List<Animal> listar() {
        return animalRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Animal> porId(Integer id) {
        return animalRepo.findById(id);
    }

    public Animal guardar(Animal a) {
        return animalRepo.save(a);
    }

    public void borrar(Integer id) {
        animalRepo.deleteById(id);
    }

    /**
     * Asigna el cuidador indicado al animal. Lanza IllegalArgumentException si
     * no existe animal o empleado, o si el empleado no es una instancia de
     * Cuidador.
     */
    public Animal asignarCuidador(Integer idAnimal, String idCuidador) {
        Animal animal = animalRepo.findById(idAnimal)
                .orElseThrow(() -> new IllegalArgumentException("Animal no encontrado: " + idAnimal));

        Empleado empleado = empleadoRepo.findById(idCuidador)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado: " + idCuidador));

        if (!(empleado instanceof Cuidador)) {
            throw new IllegalArgumentException("El empleado indicado no es un Cuidador: " + idCuidador);
        }

        animal.setCuidador((Cuidador) empleado);
        return animalRepo.save(animal);
    }

    @Transactional(readOnly = true)
    public List<Animal> listarPorCuidadorId(String idCuidador) {
        // buscar el cuidador por id; si no existe devuelve lista vacía o lanza
        Empleado emp = empleadoRepo.findById(idCuidador).orElse(null);
        if (emp == null || !(emp instanceof Cuidador)) {
            return Collections.emptyList();
        }
        return animalRepo.findByCuidador((Cuidador) emp);
    }
}
