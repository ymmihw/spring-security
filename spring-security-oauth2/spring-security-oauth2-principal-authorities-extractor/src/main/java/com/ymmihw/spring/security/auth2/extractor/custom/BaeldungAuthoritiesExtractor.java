package com.ymmihw.spring.security.auth2.extractor.custom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
@Profile("oauth2-extractors-baeldung")
public class BaeldungAuthoritiesExtractor implements AuthoritiesExtractor {

  @Override
  public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(asAuthorities(map));
  }

  private String asAuthorities(Map<String, Object> map) {
    List<String> authorities = new ArrayList<>();
    authorities.add("BAELDUNG_USER");
    @SuppressWarnings("unchecked")
    List<LinkedHashMap<String, String>> authz =
        (List<LinkedHashMap<String, String>>) map.get("authorities");
    for (LinkedHashMap<String, String> entry : authz) {
      authorities.add(entry.get("authority"));
    }
    return String.join(",", authorities);
  }
}
