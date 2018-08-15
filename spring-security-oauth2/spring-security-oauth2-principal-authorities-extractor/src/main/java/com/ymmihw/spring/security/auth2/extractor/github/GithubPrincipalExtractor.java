package com.ymmihw.spring.security.auth2.extractor.github;

import java.util.Map;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("oauth2-extractors-github")
public class GithubPrincipalExtractor implements PrincipalExtractor {
  @Override
  public Object extractPrincipal(Map<String, Object> map) {
    return map.get("login");
  }
}
