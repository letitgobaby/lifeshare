package com.letitgobaby.auth.security.login;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letitgobaby.auth.security.authentication.LoginAuthenticationToken;
import com.letitgobaby.auth.security.wrapper.RereadableRequestWrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper;

  public LoginAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
    super(requiresAuthenticationRequestMatcher);
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    log.info(" ##  LoginAuthenticationFilter ## ");
    
    String requestBody = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
    LoginDto dto = objectMapper.readValue(requestBody, LoginDto.class);

    LoginAuthenticationToken loginToken = new LoginAuthenticationToken(dto.getUserId(), dto.getUserPw());
    return super.getAuthenticationManager().authenticate(loginToken);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    RereadableRequestWrapper wRequestWrapper = new RereadableRequestWrapper((HttpServletRequest) request);
    super.doFilter(wRequestWrapper, response, chain);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }

  @Override
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }


  @Getter @Setter
  private static class LoginDto {
    String userId;
    String userPw;
  }

}
