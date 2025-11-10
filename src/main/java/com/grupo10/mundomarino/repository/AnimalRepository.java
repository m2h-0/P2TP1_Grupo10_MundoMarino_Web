package com.grupo10.mundomarino.repository;

import com.grupo10.mundomarino.entity.Animal;
import com.grupo10.mundomarino.entity.Cuidador;
import com.grupo10.mundomarino.entity.Especie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findByCuidador(Cuidador cuidador);
    List<Animal> findByEspecie(Especie especie);
}
