package com.grupo10.mundomarino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity // opcional, para @PreAuthorize y similares
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Páginas públicas
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            // Login
            .formLogin(form -> form
                .loginPage("/login")           // página de login,
                .loginProcessingUrl("/login")  // procesar POST,
                .defaultSuccessUrl("/", true)  // redirigir a
                .failureUrl("/login?error")
                .permitAll()
            )
            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            // CSRF habilitado por defecto (Spring Security)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")// deshabilitarlo para peticiones API
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // almacena contraseñas con BCrypt
    }
}
