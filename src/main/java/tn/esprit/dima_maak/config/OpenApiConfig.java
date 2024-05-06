package tn.esprit.dima_maak.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class OpenApiConfig {


        @Bean
        GroupedOpenApi openApi(){
                return GroupedOpenApi.builder().group("public")
                        .pathsToMatch("/**")
                        .build();
        }
        @Bean
        OpenAPI openAPI(){
                return new OpenAPI()
                        .info(new Info().title("Horizop  ").version("API version"));
//                        .addSecurityItem( new SecurityRequirement().addList("bearerAuth"))
//                        .components(new Components()
//                                .addSecuritySchemes("bearerAuth",new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));

        }

}
