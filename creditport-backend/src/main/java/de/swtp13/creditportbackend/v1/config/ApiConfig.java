package de.swtp13.creditportbackend.v1.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("/api/v1/") // Basispfad für alle Ressourcen unter "v1"
public class ApiConfig {

    @PostConstruct
    public void init() {
        System.out.println("Basis-URL /api/v1/ wurde gefunden.");
    }
}
