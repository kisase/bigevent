package com.mine.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {

    /**
     * @see ResourceHandlerRegistry#addResourceHandler(String...)
     */
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {

        String osName = System.getProperty("os.name");

        String fileUploadResources = "/uploads/**";

        String win = "win";

        if (osName.toLowerCase().startsWith(win)) {
            registry.addResourceHandler(fileUploadResources).addResourceLocations("file:D:\\person\\");
        } else {
            registry.addResourceHandler(fileUploadResources).addResourceLocations("file:/uploads/");
        }

    }

}


