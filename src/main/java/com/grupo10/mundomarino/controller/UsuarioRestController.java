package com.grupo10.mundomarino.controller;

import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")// habilita CORS para clientes externos
@Slf4j
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    // GET - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        log.info("GET /api/usuarios - Obteniendo todos los usuarios");
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET - Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/usuarios/{} - Obteniendo usuario por ID", id);
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Registrar un nuevo usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        log.info("POST /api/usuarios/registrar - Registrando usuario {}", usuario.getUsername());
        Usuario nuevoUsuario = usuarioService.registrarUsuario(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRol()
        );
        return ResponseEntity.ok(nuevoUsuario);
    }

    // DELETE - Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/usuarios/{} - Eliminando usuario", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
