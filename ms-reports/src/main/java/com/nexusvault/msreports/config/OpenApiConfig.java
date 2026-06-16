package com.nexusvault.msreports.config;

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
                .name("Nexus Vault Analytics Team")
                .email("reports-support@nexusvault.com")
                .url("https://nexusvault.com/analytics");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Reportes y Agregación Financiera")
                .description("API dedicada a la recolección distribuida y orquestación reactiva no bloqueante de perfiles de usuario e historiales de órdenes para consolidar e indexar auditorías analíticas inmutables.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Repositorio Base y Manual Operativo de ms-reports")
                .url("https://github.com/nexusvault/ms-reports");

        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}