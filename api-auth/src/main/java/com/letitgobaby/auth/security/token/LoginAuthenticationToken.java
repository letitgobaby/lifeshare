package com.letitgobaby.auth.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class LoginAuthenticationToken extends AbstractAuthenticationToken {

  private String userId;
  private String userPw;

  public LoginAuthenticationToken(String userId, String userPw, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.userId = userId;
    this.userPw = userPw;
  }

  public LoginAuthenticationToken(String userId, String userPw) {
    super(null);
    this.userId = userId;
    this.userPw = userPw;
  }

  public LoginAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public String getCredentials() {
    return userPw;
  }

  @Override
  public String getPrincipal() {
    return userId;
  }

}
