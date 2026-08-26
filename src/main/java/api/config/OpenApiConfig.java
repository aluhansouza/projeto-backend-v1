package api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Backend")
                        .version("v1")
                        .description("Projeto Backend")
                        .termsOfService("Projeto Backend")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://httpd.apache.org/")
                        )
                );
    }
}
