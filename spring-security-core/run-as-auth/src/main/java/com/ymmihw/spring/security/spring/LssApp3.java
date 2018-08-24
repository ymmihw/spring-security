package com.ymmihw.spring.security.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ymmihw.spring.security")
public class LssApp3 {
  public static void main(String[] args) throws Exception {
    SpringApplication.run(
        new Class[] {LssApp3.class, LssSecurityConfig.class, LssWebMvcConfiguration.class}, args);
  }
}
