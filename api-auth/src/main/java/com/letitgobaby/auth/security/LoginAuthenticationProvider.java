package com.letitgobaby.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.letitgobaby.auth.security.token.LoginAuthenticationToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userId = authentication.getPrincipal().toString();
    String userPw = authentication.getCredentials().toString();

    log.info("## LoginAuthenticationProvider ## :: " + userId + " :: " + userPw + " / " + passwordEncoder.encode(userPw));

    if (!passwordEncoder.matches(userPw, passwordEncoder.encode(userPw))) {
      throw new BadCredentialsException("Invalid password");
    }

    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return LoginAuthenticationToken.class.isAssignableFrom(authentication);
  }
  
}
