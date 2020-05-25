package ru.mcx73.gis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Для страниц, которые никак не обрабатываются сервером, а просто возвращают страницу
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/docs").setViewName("docs");
        registry.addViewController("/mfc").setViewName("mfc");

    }

}
