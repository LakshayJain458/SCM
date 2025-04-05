package com.scm.SCM.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // Your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // ← Include DELETE
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
