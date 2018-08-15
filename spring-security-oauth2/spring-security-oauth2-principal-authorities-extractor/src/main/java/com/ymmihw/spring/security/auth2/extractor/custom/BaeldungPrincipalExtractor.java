package com.ymmihw.spring.security.auth2.extractor.custom;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@Profile("oauth2-extractors-baeldung")
public class BaeldungPrincipalExtractor implements PrincipalExtractor {
  @Override
  public Object extractPrincipal(Map<String, Object> map) {
    return map.get("name");
  }
}
