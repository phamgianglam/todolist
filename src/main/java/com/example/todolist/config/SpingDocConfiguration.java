package com.example.todolist.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "BearerAuth", // Name of the security scheme
    type = SecuritySchemeType.HTTP, // Type is HTTP for JWT
    scheme = "bearer", // Scheme is "bearer"
    bearerFormat = "JWT" // Indicates the format is JWT
    )
public class SpingDocConfiguration {}
