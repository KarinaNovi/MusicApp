package com.novi.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI carDatabaseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Music APP REST API")
                        .description("My music app backend desc")
                        .version("1.0"));
    }
}