package com.grupo10.mundomarino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Páginas públicas
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                // rutas de admin
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                // Login
                .formLogin(form -> form
                .loginPage("/login") // página de login,
                .loginProcessingUrl("/login") // procesar POST,
                .defaultSuccessUrl("/", true) // redirigir a
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
