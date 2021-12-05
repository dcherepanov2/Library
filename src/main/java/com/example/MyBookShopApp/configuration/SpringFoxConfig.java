package com.example.MyBookShopApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.example.MyBookShopApp.controllers"))
                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo(){
        return new ApiInfo(
                "BookShop documentation API",
                "BookShop documentation API",
                "1.0",
                "",
                new Contact("Danil Cherepanov","https://github.com/dcherepanov2/","danilcherep7@gmail.com"),
                "api_license",
                "http://www.license.edu.org",
                new ArrayList<>()
        );
    }
}
