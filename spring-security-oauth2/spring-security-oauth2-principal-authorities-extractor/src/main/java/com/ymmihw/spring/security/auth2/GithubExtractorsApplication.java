package com.ymmihw.spring.security.auth2;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class GithubExtractorsApplication {
  public static void main(String[] args) {
    if (Strings.isEmpty(System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME))) {
      System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
          "oauth2-extractors-github");
    }

    SpringApplication.run(GithubExtractorsApplication.class, args);
  }
}
