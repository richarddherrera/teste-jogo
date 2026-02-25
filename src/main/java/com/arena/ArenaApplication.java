package com.arena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação principal Spring Boot do Arena Nexus.
 */
@SpringBootApplication
public class ArenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArenaApplication.class, args);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎮 Arena Nexus API está rodando!");
        System.out.println("📍 API: http://localhost:8080");
        System.out.println("📍 H2 Console: http://localhost:8080/h2-console");
        System.out.println("📍 Frontend: http://localhost:3000");
        System.out.println("=".repeat(60) + "\n");
    }
}
