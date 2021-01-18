package pl.motokomando.healthcare.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@ComponentScan("pl.motokomando.healthcare")
@EntityScan("pl.motokomando.healthcare")
@OpenAPIDefinition(
        info = @Info(
                title = "Healthcare Management OpenAPI",
                description = "OpenAPI Documentation for Healthcare Management",
                version = "1.0")
)
public class Application {

    public static void main(String[] args) {
        run(Application.class, args);
    }

}
