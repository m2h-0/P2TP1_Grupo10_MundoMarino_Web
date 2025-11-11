package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Contacto;
import com.grupo10.mundomarino.service.ContactoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sistema")
@CrossOrigin(origins = "*")
@Slf4j
public class ContactoRestController {

    @Autowired
    private ContactoService contactoService;

    // GET - Obtener todos los contactos
    @GetMapping
    public ResponseEntity<List<Contacto>> obtenerTodos() {
        log.info("GET /api/contactos - Obteniendo todos los contactos");
        List<Contacto> contactos = contactoService.obtenerTodosLosContactos();
        return ResponseEntity.ok(contactos);
    }

    // GET - Obtener contacto por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Contacto> obtenerPorDni(@PathVariable Integer dni) {
        log.info("GET /api/contactos/{} - Obteniendo contacto", dni);
        Contacto contacto = contactoService.obtenerContactoPorDni(dni);
        return ResponseEntity.ok(contacto);
    }

    // POST - Crear nuevo contacto
    @PostMapping
    public ResponseEntity<Contacto> crearContacto(@Valid @RequestBody Contacto contacto) {
        log.info("POST /api/contactos - Creando contacto: {}", contacto);
        Contacto nuevoContacto = contactoService.crearContacto(contacto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoContacto);
    }

    // PUT - Actualizar contacto
    @PutMapping("/{dni}")
    public ResponseEntity<Contacto> actualizarContacto(
            @PathVariable Integer dni,
            @Valid @RequestBody Contacto contacto) {
        log.info("PUT /api/contactos/{} - Actualizando contacto", dni);
        Contacto contactoActualizado = contactoService.actualizarContacto(dni, contacto);
        return ResponseEntity.ok(contactoActualizado);
    }

    // DELETE - Eliminar contacto
    @DeleteMapping("/{dni}")
    public ResponseEntity<Map<String, String>> eliminarContacto(@PathVariable Integer dni) {
        log.info("DELETE /api/contactos/{} - Eliminando contacto", dni);
        contactoService.eliminarContacto(dni);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contacto eliminado exitosamente");
        response.put("dni", dni.toString());
        
        return ResponseEntity.ok(response);
    }

    // GET - Verificar si existe DNI
    @GetMapping("/existe/{dni}")
    public ResponseEntity<Map<String, Boolean>> existeDni(@PathVariable Integer dni) {
        log.info("GET /api/contactos/existe/{} - Verificando DNI", dni);
        boolean existe = contactoService.existeDni(dni);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);
        
        return ResponseEntity.ok(response);
    }

    // GET - Buscar por criterio (apellido o DNI)
    @GetMapping("/buscar")
    public ResponseEntity<List<Contacto>> buscarPorCriterio(@RequestParam String criterio) {
        log.info("GET /api/contactos/buscar?criterio={}", criterio);
        List<Contacto> contactos = contactoService.buscarPorCriterio(criterio);
        return ResponseEntity.ok(contactos);
    }

    // GET - Buscar por apellido
    @GetMapping("/buscar/apellido/{apellido}")
    public ResponseEntity<List<Contacto>> buscarPorApellido(@PathVariable String apellido) {
        log.info("GET /api/contactos/buscar/apellido/{}", apellido);
        List<Contacto> contactos = contactoService.buscarPorApellido(apellido);
        return ResponseEntity.ok(contactos);
    }

    // GET - Buscar por DNI parcial
    @GetMapping("/buscar/dni/{dniPrefix}")
    public ResponseEntity<List<Contacto>> buscarPorDniParcial(@PathVariable String dniPrefix) {
        log.info("GET /api/contactos/buscar/dni/{}", dniPrefix);
        List<Contacto> contactos = contactoService.buscarPorDniParcial(dniPrefix);
        return ResponseEntity.ok(contactos);
    }
}