package com.urlShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class UrlShortenerApplication {

    @Bean
    public FilterRegistrationBean corsFilter1() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new
                CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean
                = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

}
