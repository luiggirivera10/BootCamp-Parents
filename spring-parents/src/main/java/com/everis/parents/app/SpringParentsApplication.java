package com.everis.parents.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@EnableSwagger2WebFlux
public class SpringParentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringParentsApplication.class, args);
  }

}
