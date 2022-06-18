package com.letitgobaby.auth.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.letitgobaby.auth.security.token.LoginAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public LoginAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
    super(requiresAuthenticationRequestMatcher);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    log.info(" ##  LoginAuthenticationFilter ##");
    
    String userId = request.getParameter("userId");
    String userPw = request.getParameter("userPw");

    log.info("  " + userId + " // " + userPw);

    LoginAuthenticationToken loginToken = new LoginAuthenticationToken(userId, userPw);
    log.info(loginToken.getPrincipal() + " / ");
    log.info(loginToken.toString() + " / ");
    return super.getAuthenticationManager().authenticate(loginToken);
  }

  @Override
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
  
}
