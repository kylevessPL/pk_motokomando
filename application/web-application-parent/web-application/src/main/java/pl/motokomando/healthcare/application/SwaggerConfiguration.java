package pl.motokomando.healthcare.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
public class SwaggerConfiguration {

    private static final String NO_ERROR_REGEX = "/error.*";

    @Bean
    public Docket api() {
        return new Docket(OAS_30)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(NO_ERROR_REGEX).negate())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Hotel Management OpenAPI")
                .description("OpenAPI Documentation for Hotel Management")
                .version("1.0")
                .build();
    }

}
