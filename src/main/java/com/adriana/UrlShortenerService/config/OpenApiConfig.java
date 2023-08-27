package com.adriana.UrlShortenerService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("URL Shortener Service")
                        .description("allow users to submit long URLs and receive short URLs that redirect to the original long URLs")
                        .version("1.0"));
    }
}
