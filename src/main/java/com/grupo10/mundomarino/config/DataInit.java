package com.grupo10.mundomarino.config;

import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario u = new Usuario();
                u.setUsername("admin");
                u.setPassword(encoder.encode("Admin123!")); // contraseña de prueba
                u.setRol("ADMIN");
                usuarioRepository.save(u);
                System.out.println("Usuario admin creado: admin / Admin123!");
            } else {
                System.out.println("Usuario admin ya existe");
            }
        };
    }
}