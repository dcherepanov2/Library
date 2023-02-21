package com.example.MyBookShopApp.configuration;

import com.example.MyBookShopApp.resolver.UserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    String valueUpload;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/image/**").addResourceLocations("file:" + valueUpload + "/");
        registry.addResourceHandler("/spring-frontend/**").addResourceLocations("file:/spring-frontend/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserHandlerMethodArgumentResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
