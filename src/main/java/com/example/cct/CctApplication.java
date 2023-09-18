package com.example.cct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@EnableJpaAuditing
@SpringBootApplication
public class CctApplication {

    public static void main(String[] args) {
        SpringApplication.run(CctApplication.class, args);
    }

    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST")
                .maxAge(3000);
    }
}
