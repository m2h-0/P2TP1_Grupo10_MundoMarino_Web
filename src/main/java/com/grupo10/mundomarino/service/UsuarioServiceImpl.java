package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosLosUsuarios() {
        log.info("Obteniendo todos los usuarios");
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        log.info("Buscando usuario con id {}", id);
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario registrarUsuario(String username, String rawPassword, String rol) {
        log.info("Registrando nuevo usuario {}", username);
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(rawPassword)); // encriptar contraseña
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        log.info("Eliminando usuario con id {}", id);
        usuarioRepository.deleteById(id);
    }
}
