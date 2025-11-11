package com.grupo10.mundomarino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

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
            )
            // registrar AccessDeniedHandler para manejar 403s
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler())
            );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // si no hay principal (usuario) autenticado, redirigir a login (genera un token CSRF nuevo)
            String ctx = request.getContextPath(); // "/mundomarino" when app is deployed under that context
            String loginPath = ctx + "/login";
            if (request.getUserPrincipal() == null) {
                response.sendRedirect(loginPath);
            } else {
                // (no implementado) si está autenticado pero no autorizado -> opcional: página personalizada
                // response.sendRedirect(ctx + "/access-denied");
                response.sendRedirect(ctx);// cambiar a ctx + "/access-denied"
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // almacena contraseñas con BCrypt
    }
}
