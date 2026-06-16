package com.nexusvault.msusers.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//1 - @ Configuration
@Configuration
public class OpenApiConfig {

    //- 2 @Bean
    @Bean
    //3 - public OpenAPI configurarOpenApi() {
    public OpenAPI configurarOpenApi() {

        Contact contacto = new Contact()
                .name("Nexus Vault Core Team")
                .email("users-support@nexusvault.com")
                .url("https://nexusvault.com/users");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Gestión de Usuarios y Perfiles")
                .description("API encargada del ciclo de vida de las cuentas de jugadores, control de avatares, métricas de reputación interna de la comunidad y vinculación con el módulo de autenticación central.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Especificaciones y Repositorio de ms-users")
                .url("https://github.com/nexusvault/ms-users");

        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}