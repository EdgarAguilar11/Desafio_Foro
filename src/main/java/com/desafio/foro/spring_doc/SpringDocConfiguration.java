package com.desafio.foro.spring_doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("API Voll.med")
                        .description("API Rest de la aplicación para el desafio de foro, que contiene las funcionalidades de CRUD de topicos asi como la autenticacion mediante spring security mediante token")
                        .contact(new Contact()
                                .name("Edgar Aguilar")
                                .email("edd_3625@hotmail.com"))
                        .license(new License()
                                .name("Edgar")
                                .url("..........")));
    }
}
