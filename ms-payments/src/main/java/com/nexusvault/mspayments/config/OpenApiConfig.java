package com.nexusvault.mspayments.config;

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
                .name("Nexus Vault Gateway Billing Team")
                .email("payments-gateway@nexusvault.com")
                .url("https://nexusvault.com/payments");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Procesamiento de Pagos y Reembolsos")
                .description("API encargada del flujo transaccional con pasarelas de pago bancarias, validaciones de montos mínimos, auditoría de transacciones externas y sincronización asíncrona hacia las billeteras virtuales.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Especificaciones Técnicas y Webhooks de ms-payments")
                .url("https://github.com/nexusvault/ms-payments");

        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}