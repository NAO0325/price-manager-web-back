package com.price.manager.back.driving.controllers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:" + serverPort))
                .info(new Info()
                        .title("Price Manager API")
                        .version("1.0")
                        .description("Price Management System API Documentation")
                        .contact(new Contact()
                                .name("Napoleon Avila Ochoa")
                                .email("napoavi@gmail.com")));
    }
}