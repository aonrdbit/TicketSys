package com.zxc.ticketsys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedMethods("POST,GET, OPTIONS,DELETE,PUT")
                .allowedOrigins("http://127.0.0.1:8080,http://localhost:8080")
                .allowedHeaders("Content-Type,ContentType,Access-Control-Allow-Headers, Authorization, X-Requested-With Access-Control-Allow-Origin")
                .allowCredentials(true);
    }
}