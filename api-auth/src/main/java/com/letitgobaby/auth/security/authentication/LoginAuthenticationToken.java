package com.letitgobaby.auth.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class LoginAuthenticationToken extends AbstractAuthenticationToken {

  private String userId;
  private String userPw;

  public LoginAuthenticationToken(String userId, String userPw) {
    super(null);
    this.userId = userId;
    this.userPw = userPw;
    super.setAuthenticated(false);
  }

  public LoginAuthenticationToken(String userId, boolean isAuth) {
    super(null);
    this.userId = userId;
    this.userPw = null;
    super.setAuthenticated(isAuth);
  }

  public LoginAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public String getCredentials() {
    return this.userPw;
  }

  @Override
  public String getPrincipal() {
    return this.userId;
  }

}
