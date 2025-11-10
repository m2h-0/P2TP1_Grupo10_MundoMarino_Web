package com.grupo10.mundomarino.service;

import com.grupo10.mundomarino.entity.Empleado;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.EmpleadoRepository;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository, EmpleadoRepository empleadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Obtener información del empleado si existe
        Optional<Empleado> empleado = empleadoRepository.findById(username);
        
        return User.withUsername(usuario.getUsername())
                   .password(usuario.getPassword())
                   .roles(usuario.getRol()) // ADMIN, GUIA, CUIDADOR
                   .build();
    }
}
