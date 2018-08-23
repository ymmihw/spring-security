package com.ymmihw.spring.security;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GreetController {

  private GreetService greetService;

  public GreetController(GreetService greetService) {
    this.greetService = greetService;
  }

  @GetMapping("/")
  public Mono<String> greet(Mono<Principal> principal) {
    return principal.map(Principal::getName).map(name -> String.format("Hello, %s", name));
  }

  @GetMapping("/admin")
  public Mono<String> greetAdmin(Mono<Principal> principal) {
    return principal.map(Principal::getName).map(name -> String.format("Admin access: %s", name));
  }

  @GetMapping("/greetService")
  public Mono<String> greetService() {
    return greetService.greet();
  }

}