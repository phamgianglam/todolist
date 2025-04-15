package com.example.todolist.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticFileConfiguration implements WebMvcConfigurer {
  @Value("${app.upload.dir}")
  private String imagePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    Path uploadPath = Paths.get(imagePath).toAbsolutePath();

    registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadPath.toString());
  }
}
