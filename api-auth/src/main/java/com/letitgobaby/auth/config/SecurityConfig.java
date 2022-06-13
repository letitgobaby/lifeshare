package com.letitgobaby.auth.config;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    http.cors(); // corsFilter 이름의 빈 등록이 되면 자동 적용
    http.csrf().disable();
    http.headers().frameOptions().disable();

    http
      .sessionManagement(sseion -> {
        sseion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
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
  private Filter loginFilter() {
    RequestMatcher login_requestMatcher = new AntPathRequestMatcher("/login", "POST");
    return new LoginAuthenticationFilter(login_requestMatcher);
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
