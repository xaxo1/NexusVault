package com.nexusvault.msorders.config;

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
                .name("Nexus Vault Core Checkout Team")
                .email("orders-support@nexusvault.com")
                .url("https://nexusvault.com/orders");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Órdenes y Transacciones")
                .description("API encargada de la creación de boletas de compra, gestión de estados de pago y orquestación asíncrona reactiva con el catálogo y pasarela de saldo de los usuarios.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Manual de Arquitectura Reactiva y Flujo de ms-orders")
                .url("https://github.com/nexusvault/ms-orders");

        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}