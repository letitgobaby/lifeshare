package com.letitgobaby.auth.security.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserInfoService implements AuthenticationUserDetailsService<Authentication> {

  @Override
  public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
    token.getPrincipal();
    return null;
  }
  
}
