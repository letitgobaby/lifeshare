package com.letitgobaby.auth.config;

import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.letitgobaby.auth.security.JwtAuthenticationFilter;
import com.letitgobaby.auth.security.LoginAuthenticationFilter;
import com.letitgobaby.auth.security.LoginAuthenticationProvider;
import com.letitgobaby.auth.security.LoginFailureHandler;
import com.letitgobaby.auth.security.LoginSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.httpBasic().disable();
    http.cors(); // corsFilter 이름의 빈 등록이 되면 자동 적용
    http.csrf().disable();
    http.headers().frameOptions().disable();

    http
      .sessionManagement(sseion -> {
        sseion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
      .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
      .requestMatchers(matchers -> {
        matchers.antMatchers("/static/**");
      })
      .authorizeHttpRequests(authorize -> {
        authorize.anyRequest().permitAll();
      })
      .exceptionHandling()
      .authenticationEntryPoint((request, response, ex) -> {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
      })
      .accessDeniedHandler((request, response, ex) -> {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      });

    return http.build();
  }

  @Bean
  public Filter jwtFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  public Filter loginFilter() {
    RequestMatcher login_requestMatcher = new AntPathRequestMatcher("/login", "POST");
    LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter(login_requestMatcher);
    loginFilter.setAuthenticationManager(loginProvider());
    loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
    loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
    
    return loginFilter;
  }

  @Bean
  public AuthenticationManager loginProvider() {
    return new ProviderManager(Arrays.asList(new LoginAuthenticationProvider(bCryptPasswordEncoder())));
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

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
