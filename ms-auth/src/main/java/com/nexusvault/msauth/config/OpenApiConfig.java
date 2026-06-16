package com.nexusvault.msauth.config;

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

        // Información de contacto
        Contact contacto = new Contact()
                .name("Nexus Vault Security Team")
                .email("security@nexusvault.com")
                .url("https://nexusvault.com");

        // Licencia del proyecto
        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        // Información principal de la API
        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Autenticación")
                .description("Servicio encargado de la emisión de tokens JWT, control de accesos y cifrado de credenciales.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        // Documentación externa (GitHub)
        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Especificaciones del Protocolo de Seguridad")
                .url("https://github.com/nexusvault/ms-auth");

        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}