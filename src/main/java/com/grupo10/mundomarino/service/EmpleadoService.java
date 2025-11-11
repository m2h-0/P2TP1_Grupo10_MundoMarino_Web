package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Empleado;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repo;

    public EmpleadoService(EmpleadoRepository repo) {
        this.repo = repo;
    }

    public List<Empleado> listar() {
        return repo.findAll();
    }

    public Empleado guardar(Empleado e) {
        return repo.save(e);
    }

    public Optional<Empleado> porId(String id) {
        return repo.findById(id);
    }

    public void borrar(String id) {
        repo.deleteById(id);
    }
}
