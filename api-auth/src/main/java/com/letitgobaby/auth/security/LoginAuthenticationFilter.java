package com.letitgobaby.auth.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public LoginAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
    super(requiresAuthenticationRequestMatcher);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    
    String userId = "test";
    String userPw = "test";

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, userPw);
    return super.getAuthenticationManager().authenticate(authRequest);
  }
  
}
