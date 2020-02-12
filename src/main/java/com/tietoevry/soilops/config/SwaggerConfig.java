package com.tietoevry.soilops.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    private static final String SECURITY_REFERENCE_JWT = "JWT";
    private static final String SECURITY_REFERENCE_OAUTH2 = "OAuth2";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Internal server error, an unexpected error has occurred on the server.")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden, the user doesn't have the proper user rights to access the resource.")
                                        .build()))
                .securityContexts(newArrayList(securityContext()))
                .securitySchemes(newArrayList(jwtApiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tietoevry.soilops"))
                .paths(regex("/.*"))
                .build();
    }
    

    private ApiKey jwtApiKey() {
        return new ApiKey(SECURITY_REFERENCE_JWT, AUTHORIZATION_HEADER, "header");
    }


    private SecurityContext securityContext() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        return SecurityContext.builder()
                .securityReferences(newArrayList(
                        new SecurityReference(SECURITY_REFERENCE_JWT, new AuthorizationScope[]{authorizationScope}),
                        new SecurityReference(SECURITY_REFERENCE_OAUTH2, new AuthorizationScope[]{authorizationScope})))
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("REST api documentation for SoilOps",
                "This is the REST api documentation for the SoilOps server.",
                "1.0",
                "urn:tos",
                (Contact) null,
                "GPLv3", "https://www.gnu.org/licenses/gpl-3.0.en.html",
                Collections.emptyList());
    }
}