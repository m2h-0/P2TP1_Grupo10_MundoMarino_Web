package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    // Obtener todos los usuarios
    List<Usuario> obtenerTodosLosUsuarios();

    // Obtener un usuario por ID
    Optional<Usuario> obtenerUsuarioPorId(Long id);

    // Registrar un nuevo usuario
    Usuario registrarUsuario(String username, String rawPassword, String rol);

    // Eliminar usuario por ID
    void eliminarUsuario(Long id);
}
