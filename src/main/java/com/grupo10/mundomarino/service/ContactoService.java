package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Contacto;

import java.util.List;
//import org.springframework.stereotype.Service;

public interface ContactoService {

    // Obtener todos los contactos
    List<Contacto> obtenerTodosLosContactos();

    // Obtener un contacto por DNI
    Contacto obtenerContactoPorDni(Integer dni);

    // Crear un nuevo contacto
    Contacto crearContacto(Contacto contacto);

    // Actualizar un contacto existente
    Contacto actualizarContacto(Integer dni, Contacto contacto);

    // Eliminar un contacto por DNI
    void eliminarContacto(Integer dni);

    // Verificar si existe un DNI
    boolean existeDni(Integer dni);

    // Buscar contactos por apellido
    List<Contacto> buscarPorApellido(String apellido);

    // Buscar contactos por DNI (parcial)
    List<Contacto> buscarPorDniParcial(String dniPrefix);

    // Búsqueda combinada (apellido o DNI)
    List<Contacto> buscarPorCriterio(String criterio);
}