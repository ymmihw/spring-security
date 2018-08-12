package com.ymmihw.spring.security.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SampleLDAPApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SampleLDAPApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer adapter() {
    return new WebMvcConfigurer() {
      @Override
      public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
      }
    };
  }

}
