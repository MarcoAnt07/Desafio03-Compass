package io.github.marcoant07.ms_ticket_manager.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API MS Ticket Manager")
                                .description("MS Ticket Manager API Documentation")
                                .version("1.0")
                );
    }
}
