package configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Inventario",
                version = "1.0.0",
                description = "This is aplicacion de inventario"
        )
)
public class OpenApiConfig {

}
