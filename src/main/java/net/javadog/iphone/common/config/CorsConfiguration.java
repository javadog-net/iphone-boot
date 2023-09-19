package net.javadog.iphone.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 跨域处理
 * @Author: hdx
 * @Date: 2022/1/13 16:19
 * @Version:-【】 1.0
 */
@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 设置允许跨域的路由
                registry.addMapping("/**")
                        // 设置允许跨域请求的域名
                        .allowedOriginPatterns("*")
                        // 是否允许证书（cookies）
                        .allowCredentials(true)
                        // 设置允许的方法
                        .allowedMethods("*")
                        // 跨域允许时间
                        .maxAge(3600);
            }
        };
    }
}
