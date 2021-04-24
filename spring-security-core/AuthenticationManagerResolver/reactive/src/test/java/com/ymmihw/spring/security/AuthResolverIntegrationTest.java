package com.ymmihw.spring.security;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AuthResolverApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AuthResolverIntegrationTest {
  @Autowired
  private WebTestClient testClient;

  @Test
  public void givenCustomerCredential_whenWelcomeCustomer_thenExpectOk() {
    testClient.get().uri("/customer/welcome")
        .header("Authorization",
            "Basic " + Base64Utils.encodeToString("customer1:pass1".getBytes()))
        .exchange().expectStatus().isOk();
  }

  @Test
  public void givenEmployeeCredential_whenWelcomeCustomer_thenExpect401Status() {
    testClient.get().uri("/customer/welcome")
        .header("Authorization",
            "Basic " + Base64Utils.encodeToString("employee1:pass1".getBytes()))
        .exchange().expectStatus().isUnauthorized();
  }

  @Test
  public void givenEmployeeCredential_whenWelcomeEmployee_thenExpectOk() {
    testClient.get().uri("/employee/welcome")
        .header("Authorization",
            "Basic " + Base64Utils.encodeToString("employee1:pass1".getBytes()))
        .exchange().expectStatus().isOk();
  }

  @Test
  public void givenCustomerCredential_whenWelcomeEmployee_thenExpect401Status() {
    testClient.get().uri("/employee/welcome")
        .header("Authorization",
            "Basic " + Base64Utils.encodeToString("customer1:pass1".getBytes()))
        .exchange().expectStatus().isUnauthorized();
  }
}
