package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Contacto;
import com.grupo10.mundomarino.exception.ContactoException;
import com.grupo10.mundomarino.repository.ContactoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ContactoServiceImpl implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> obtenerTodosLosContactos() {
        log.info("Obteniendo todos los contactos");
        return contactoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contacto obtenerContactoPorDni(Integer dni) {
        log.info("Buscando contacto con DNI: {}", dni);
        return contactoRepository.findById(dni)
                .orElseThrow(() -> new ContactoException("Contacto con DNI " + dni + " no encontrado"));
    }

    @Override
    public Contacto crearContacto(Contacto contacto) {
        log.info("Creando nuevo contacto: {}", contacto);
        
        // Verificar si el DNI ya existe
        if (contactoRepository.existsByDni(contacto.getDni())) {
            throw new ContactoException("Ya existe un contacto con el DNI " + contacto.getDni());
        }
        
        // Verificar si el correo ya existe
        if (contactoRepository.findByCorreo(contacto.getCorreo()).isPresent()) {
            throw new ContactoException("Ya existe un contacto con el correo " + contacto.getCorreo());
        }
        
        return contactoRepository.save(contacto);
    }

    @Override
    public Contacto actualizarContacto(Integer dni, Contacto contacto) {
        log.info("Actualizando contacto con DNI: {}", dni);
        
        Contacto contactoExistente = obtenerContactoPorDni(dni);
        
        // Verificar si el correo ya existe en otro contacto
        contactoRepository.findByCorreo(contacto.getCorreo()).ifPresent(c -> {
            if (!c.getDni().equals(dni)) {
                throw new ContactoException("El correo " + contacto.getCorreo() + " ya está registrado en otro contacto");
            }
        });
        
        // Actualizar campos
        contactoExistente.setNombres(contacto.getNombres());
        contactoExistente.setApellidos(contacto.getApellidos());
        contactoExistente.setFechaNacimiento(contacto.getFechaNacimiento());
        contactoExistente.setCorreo(contacto.getCorreo());
        
        if (contacto.getFoto() != null && !contacto.getFoto().isEmpty()) {
            contactoExistente.setFoto(contacto.getFoto());
        }
        
        return contactoRepository.save(contactoExistente);
    }

    @Override
    public void eliminarContacto(Integer dni) {
        log.info("Eliminando contacto con DNI: {}", dni);
        
        Contacto contacto = obtenerContactoPorDni(dni);
        contactoRepository.delete(contacto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDni(Integer dni) {
        return contactoRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> buscarPorApellido(String apellido) {
        log.info("Buscando contactos por apellido: {}", apellido);
        return contactoRepository.findByApellidosStartingWithIgnoreCase(apellido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> buscarPorDniParcial(String dniPrefix) {
        log.info("Buscando contactos por DNI parcial: {}", dniPrefix);
        return contactoRepository.findByDniStartingWith(dniPrefix);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contacto> buscarPorCriterio(String criterio) {
        log.info("Buscando contactos por criterio: {}", criterio);
        
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodosLosContactos();
        }
        
        return contactoRepository.buscarPorApellidoODni(criterio.trim());
    }
}