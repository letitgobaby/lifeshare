package com.letitgobaby.auth.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private String accessToken;
  private String refeshToken;

  public JwtAuthenticationToken(String accessToken) {
    super(null);
    this.accessToken = accessToken;
    super.setAuthenticated(false);
  }

  public JwtAuthenticationToken(String accessToken, String refeshToken) {
    super(null);
    this.accessToken = accessToken;
    this.refeshToken = refeshToken;
    super.setAuthenticated(false);
  }

  public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public String getCredentials() {
    return this.refeshToken;
  }

  @Override
  public String getPrincipal() {
    return this.accessToken;
  }
  
}
