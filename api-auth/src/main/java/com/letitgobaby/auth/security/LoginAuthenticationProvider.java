package com.letitgobaby.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.letitgobaby.auth.entity.UserMock;
import com.letitgobaby.auth.enums.AuthErrorCode;
import com.letitgobaby.auth.security.authentication.LoginAuthenticationToken;
import com.letitgobaby.auth.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.info("## LoginAuthenticationProvider ##");
    String userId = authentication.getPrincipal().toString();
    String userPw = authentication.getCredentials().toString();

    UserMock user = this.userService.loginByUserId(userId);
    if (user == null) {
      throw new BadCredentialsException(AuthErrorCode.USER_NOT_FOUND.getMessage());
    }

    String mockEncodedPasswd = passwordEncoder.encode(user.getPassword());
    if (!passwordEncoder.matches(userPw, mockEncodedPasswd)) {
      throw new BadCredentialsException(AuthErrorCode.PASSWORD_NOT_MATCH.getMessage());
    }

    return new LoginAuthenticationToken(user.getAccountId(),true);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return LoginAuthenticationToken.class.isAssignableFrom(authentication);
  }
  
}
