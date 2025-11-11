package com.grupo10.mundomarino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaDeGestionMundoMarinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeGestionMundoMarinoApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Aplicación Sistema de Gestión Mundo Marino iniciada!");
        System.out.println("URL: http://localhost:8080/mundomarino");
        System.out.println("API REST: http://localhost:8080/mundomarino/api/sistema");
        System.out.println("==============================================");
	}

}
