package com.ymmihw.spring.security.auth2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@Profile("classic")
public class ClassicSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated().and().oauth2Login()
        .clientRegistrationRepository(clientRegistrationRepository());
  }


  private static List<String> clients = Arrays.asList("google", "github");

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    List<ClientRegistration> registrations = clients.stream().map(c -> getRegistration(c))
        .filter(registration -> registration != null).collect(Collectors.toList());

    return new InMemoryClientRegistrationRepository(registrations);
  }

  private static String CLIENT_PROPERTY_KEY = "classic.spring.security.oauth2.client.registration.";

  @Autowired
  private Environment env;

  private ClientRegistration getRegistration(String client) {
    String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");

    if (clientId == null) {
      return null;
    }

    String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");
    if (client.equals("google")) {
      return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(clientId)
          .clientSecret(clientSecret).build();
    }
    if (client.equals("github")) {
      return CommonOAuth2Provider.GITHUB.getBuilder(client).clientId(clientId)
          .clientSecret(clientSecret).build();
    }
    return null;
  }
}
