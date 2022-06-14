package com.letitgobaby.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

// @Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userId = authentication.getName();
    String userPw = authentication.getCredentials().toString();


    if (!passwordEncoder.matches("raw", "encodedPassword")) {
      throw new BadCredentialsException("Invalid password");
    }

    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // TODO Auto-generated method stub
    return false;
  }
  
}
