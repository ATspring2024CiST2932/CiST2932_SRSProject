// src/main/java/com/CiST2932/WebConfig.java
// This file is used to configure CORS (Cross-Origin Resource Sharing) for the Spring Boot application.

package com.CiST2932.SRSProject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Allow all headers
                .exposedHeaders("Authorization", "Content-Type") // Expose specific headers
                .allowCredentials(true) // Allow credentials
                .maxAge(3600); // Cache preflight response for 1 hour
    }
}
