package com.nexusvault.mswallet.config;

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
                .name("Nexus Vault Financial Engineering")
                .email("wallet-support@nexusvault.com")
                .url("https://nexusvault.com/finance");

        License licencia = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info informacion = new Info()
                .title("Nexus Vault - Microservicio de Billetera y Control Financiero")
                .description("API encargada del procesamiento transaccional, administración de fondos precisos en decimales, cobros automáticos sincronizados y pasarela interna para la compra de skins.")
                .version("1.0.0")
                .contact(contacto)
                .license(licencia);

        ExternalDocumentation docExterna = new ExternalDocumentation()
                .description("Manual de Operación y Arquitectura ms-wallet")
                .url("https://github.com/nexusvault/ms-wallet");

        return new OpenAPI()
                .info(informacion)
                .externalDocs(docExterna);
    }
}