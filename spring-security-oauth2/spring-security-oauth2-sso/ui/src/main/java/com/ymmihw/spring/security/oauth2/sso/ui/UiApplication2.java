package com.ymmihw.spring.security.oauth2.sso.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@Profile("ui2")
public class UiApplication2 extends SpringBootServletInitializer {

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(UiApplication2.class);
    application.setAdditionalProfiles("ui2");
    application.run(args);
  }
}
