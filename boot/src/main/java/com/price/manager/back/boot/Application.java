package com.price.manager.back.boot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@OpenAPIDefinition
@ComponentScan(basePackages = {
        "com.price.manager.back.application",
        "com.price.manager.back.boot",
        "com.price.manager.back.driven.repositories",
        "com.price.manager.back.driving.controllers"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
