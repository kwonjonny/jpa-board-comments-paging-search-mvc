package board.comment.search.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

//http://localhost:8080/swagger-ui/index.html#/
@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Joonny Swagger",version = "v1"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("Joon OPEN API v1")
                .pathsToMatch(paths)
                .build();
    }
}
