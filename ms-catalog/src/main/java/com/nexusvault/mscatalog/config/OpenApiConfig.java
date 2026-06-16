package com.nexusvault.mscatalog.config;

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
                .name("Nexus Vault Support Team")
                .email("catalog-support@nexusvault.com")
                .url("https://nexusvault.com/catalog");

        // Licencia del proyecto
        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        // Información principal de la API
        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Catálogo de Productos")
                .description("API encargada de la gestión integral del inventario de ítems, filtrados por rareza y control de ofertas del sistema.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        // Documentación externa (GitHub)
        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Repositorio y Manual Técnico de ms-catalog")
                .url("https://github.com/nexusvault/ms-catalog");

        // Configuración OpenAPI
        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}