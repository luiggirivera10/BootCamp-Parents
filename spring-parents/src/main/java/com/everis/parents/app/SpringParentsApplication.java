package com.everis.parents.app;

import com.everis.parents.app.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
@EnableEurekaClient
public class SpringParentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringParentsApplication.class, args);
  }
  
  public void addResourceHandlers(ResourceHandlerRegistry regisry) {
    regisry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/");
  }

}
