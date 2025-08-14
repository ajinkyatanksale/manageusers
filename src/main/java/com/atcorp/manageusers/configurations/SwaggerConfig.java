package com.atcorp.manageusers.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description("API documentation for User Management Service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ajinkya Tanksale")
                                .email("tanksale.ajinkya3@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

