package com.management.bookstore.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080/"))
                //.components(new Components().addSecuritySchemes("bearer-key",
                //		new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Survey application").description("demo application for books store")
                        .termsOfService("").version("1.0.0").license(new License().name("").url("http://moin.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact().email("4u2moin@gmail.com")));
    }
}