package com.financialadvisor.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        Dotenv dotenv = Dotenv.load();
        System.out.println("✅ Loaded MongoDB URI: " + dotenv.get("MONGODB_URI")); // Debugging log
        return dotenv;
    }
}
