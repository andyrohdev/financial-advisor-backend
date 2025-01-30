package com.financialadvisor;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialAdvisorApplication {
    public static void main(String[] args) {
        // Manually load .env file
        Dotenv dotenv = Dotenv.load();
        System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));

        System.out.println("Loaded MongoDB URI: " + dotenv.get("MONGODB_URI"));


        SpringApplication.run(FinancialAdvisorApplication.class, args);
    }
}
