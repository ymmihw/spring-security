package com.ymmihw.spring.security.auth2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    // new SpringApplicationBuilder(Application.class).profiles("classic").run(args);
    new SpringApplicationBuilder(Application.class).profiles("boot").run(args);
  }
}
