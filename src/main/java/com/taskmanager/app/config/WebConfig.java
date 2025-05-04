package com.taskmanager.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Áp dụng CORS cho tất cả các API
                .allowedOrigins("http://localhost:5173") // Cho phép yêu cầu từ frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP cho phép
                .allowedHeaders("Authorization", "Content-Type", "Accept") // Các header cho phép
                .allowCredentials(true) // Cho phép cookies và thông tin xác thực
                .maxAge(3600); // Thời gian lưu cache cho preflight request (3600 giây = 1 giờ)
    }
}
