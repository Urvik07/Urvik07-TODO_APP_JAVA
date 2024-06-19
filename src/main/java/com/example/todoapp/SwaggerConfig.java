package com.example.todoapp;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

@EnableWebMvc
@EnableSwagger2
@Component
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.todoapplication.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .consumes(Set.of(MediaType.APPLICATION_JSON_VALUE))
                .useDefaultResponseMessages(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Todo App API")
                .description("API documentation for the Todo App")
                .version("1.0.0")
                .build();
    }
}
