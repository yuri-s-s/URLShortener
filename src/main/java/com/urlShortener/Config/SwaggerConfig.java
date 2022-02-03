package com.urlShortener.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String TAG_1 = "User Controller";
    public static final String TAG_2 = "Url Controller";
    public static final String TAG_3 = "Role Controller";
    public static final String TAG_4 = "ShortUrl Controller";
    public static final String TAG_5 = "Authenticate Controller";

    @Bean
    public Docket api() {

        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey(HttpHeaders.AUTHORIZATION, "Bearer", "header"));

        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .apiInfo(metaData())
                .tags(
                        new Tag(TAG_1, "This is a controller of users"),
                        new Tag(TAG_2, "This is a controller of urls"),
                        new Tag(TAG_3, "This is a controller of roles"),
                        new Tag(TAG_4, "This is a controller of short urls"),
                        new Tag(TAG_5, "This is a controller of authenticate")
                        )
                ;
    }

    private ApiInfo metaData() {

        Contact contact = new Contact("Yuri Souza", "localhost:8080", "yuri.santos@email.com");

        return new ApiInfo(
                "Url Shortener",
                "Shortener Url",
                "1.0",
                "ok",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()

        );
    }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }
}
