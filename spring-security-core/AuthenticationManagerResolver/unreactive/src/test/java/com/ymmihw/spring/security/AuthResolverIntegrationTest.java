package com.ymmihw.spring.security;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AuthResolverApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AuthResolverIntegrationTest {

  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        .apply(springSecurity(springSecurityFilterChain)).build();
  }

  @Test
  public void givenCustomerCredential_whenWelcomeCustomer_thenExpectOk() throws Exception {
    this.mockMvc
        .perform(get("/customer/welcome").header("Authorization",
            String.format("Basic %s", Base64Utils.encodeToString("customer1:pass1".getBytes()))))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void givenEmployeeCredential_whenWelcomeCustomer_thenExpect401Status() throws Exception {
    this.mockMvc
        .perform(get("/customer/welcome").header("Authorization",
            "Basic " + Base64Utils.encodeToString("employee1:pass1".getBytes())))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void givenEmployeeCredential_whenWelcomeEmployee_thenExpectOk() throws Exception {
    this.mockMvc
        .perform(get("/employee/welcome").header("Authorization",
            "Basic " + Base64Utils.encodeToString("employee1:pass1".getBytes())))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void givenCustomerCredential_whenWelcomeEmployee_thenExpect401Status() throws Exception {
    this.mockMvc
        .perform(get("/employee/welcome").header("Authorization",
            "Basic " + Base64Utils.encodeToString("customer1:pass1".getBytes())))
        .andExpect(status().isUnauthorized());
  }
}
