package org.digilinq.platform.users.configuration;

import org.digilinq.platform.users.web.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer webConfigurer(LoggingInterceptor loggingInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // Applies to all endpoints
                        .allowedOrigins("*")  // Specify your frontend URL here
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .exposedHeaders("Location");  // Expose the Location header
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loggingInterceptor);
            }
        };
    }
}