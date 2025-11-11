package com.grupo10.mundomarino.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInicialConfig {

    @Bean
    public ApplicationRunner crearAdminInicial(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsername = "admin";
            if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {// si no existe
                Usuario admin = new Usuario();                              // crear admin
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode("adm.10"));        // password por defecto
                admin.setRol("ADMIN");
                usuarioRepository.save(admin);
            }
        };
    }
}