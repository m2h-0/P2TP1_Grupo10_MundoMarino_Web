package com.grupo10.mundomarino.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.grupo10.mundomarino.entity.Usuario;
import com.grupo10.mundomarino.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitUsersConfig {

    @Bean
    public ApplicationRunner crearUsuariosIniciales(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String[][] usuariosIniciales = {
                {"admin", "adm.10", "ADMIN"},
                {"guia", "guia.10", "GUIA"},
                {"cuidador", "cuid.10", "CUIDADOR"}
            };

            for (String[] u : usuariosIniciales) {
                String username = u[0];
                String rawPassword = u[1];
                String rol = u[2];

                if (usuarioRepository.findByUsername(username).isEmpty()) {
                    Usuario usuario = new Usuario();
                    usuario.setUsername(username);
                    usuario.setPassword(passwordEncoder.encode(rawPassword));
                    usuario.setRol(rol);
                    usuarioRepository.save(usuario);
                }
            }
        };
    }
}