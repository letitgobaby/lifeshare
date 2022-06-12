package com.letitgobaby.auth.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable();
    http.cors();
    http.csrf().disable();
    http.headers().frameOptions().disable();

    http
      .sessionManagement(sseion -> {
        sseion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
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
