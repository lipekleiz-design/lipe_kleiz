package com.lipe_kleiz.delivery_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Delivery API")
                        .version("1.0")
                        .description("API para gerenciamento de entregas")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@delivery.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local do Spring Boot")))

                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,

                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Informe apenas o token JWT obtido em /api/auth/login.")));
    }

}