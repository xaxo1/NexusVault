package com.nexusvault.msinventory.config;

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
                .name("Nexus Vault Logistics Team")
                .email("inventory-ops@nexusvault.com")
                .url("https://nexusvault.com/inventory");

        // Licencia del proyecto
        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        // Información principal de la API
        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Inventario y Stock")
                .description("API dedicada al control físico y lógico de existencias de productos, adiciones de stock y gestión de alertas por desabastecimiento.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        // Documentación externa (GitHub)
        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Manual de Operaciones y Despliegue de ms-inventory")
                .url("https://github.com/nexusvault/ms-inventory");

        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}