package com.letitgobaby.auth.config;

import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.letitgobaby.auth.security.filter.JwtAuthenticationFilter;
import com.letitgobaby.auth.security.filter.LoginAuthenticationFilter;
import com.letitgobaby.auth.security.handler.LoginFailureHandler;
import com.letitgobaby.auth.security.handler.LoginSuccessHandler;
import com.letitgobaby.auth.security.provider.JwtValidateProvider;
import com.letitgobaby.auth.security.provider.LoginAuthenticationProvider;
import com.letitgobaby.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final String[] PERMIT_URL = new String[] { "/login" };

  private final PasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.httpBasic().disable();
    http.cors(); // corsFilter 이름의 빈 등록이 되면 자동 적용
    http.csrf().disable(); // API서버이기 때문에 xss 공격 설정 필요없음

    http
      .sessionManagement(sseion -> sseion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .requestMatchers(matchers -> matchers.antMatchers("/static/**"))
      .authorizeHttpRequests(authorize -> {
        authorize
          .antMatchers(PERMIT_URL).permitAll()
          .anyRequest().authenticated();
      })
      .exceptionHandling()
      .authenticationEntryPoint((req, res, ex) -> {
        res.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
      })
      .accessDeniedHandler((req, res, ex) -> {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      });

    http
      .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
      .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public Filter loginFilter() {
    String LOGIN_URL = "/login";
    RequestMatcher login_requestMatcher = new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name());
    LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter(login_requestMatcher);
    loginFilter.setAuthenticationManager(loginAuthManager());
    loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
    loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
    return loginFilter;
  }

  @Bean
  public AuthenticationManager loginAuthManager() {
    LoginAuthenticationProvider provider = new LoginAuthenticationProvider();
    provider.setEncoder(this.bCryptPasswordEncoder);
    provider.setUserService(this.userService);
    return new ProviderManager(Arrays.asList(provider));
  }

  @Bean
  public Filter jwtFilter() {
    JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter();
    jwtFilter.setAuthenticationManager(jwtAuthManager());
    return jwtFilter();
  }

  @Bean
  public AuthenticationManager jwtAuthManager() {
    JwtValidateProvider provider = new JwtValidateProvider();
    return new ProviderManager(Arrays.asList(provider));
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
