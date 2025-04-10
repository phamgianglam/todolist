package com.example.todolist.config;

import com.example.todolist.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration { // Removed extends WebSecurityConfiguration
  private final JwtRequestFilter jwtRequestFilter;

  @Value("${jwt.enabled:true}")
  private boolean jwtEnabled;

  public SecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    if (jwtEnabled) {
      http.csrf(AbstractHttpConfigurer::disable)
          .cors(AbstractHttpConfigurer::disable)
          // .exceptionHandling(exceptionHandling ->
          // exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
          // )
          .sessionManagement(
              session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(
              auth ->
                  auth.requestMatchers(
                          "/api/v1/auth/**",
                          "/api/v1/register/**",
                          "/api-docs/**",
                          "/swagger-ui/**")
                      .permitAll()
                      .anyRequest()
                      .authenticated())
          .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    } else {
      http.csrf(AbstractHttpConfigurer::disable)
          .cors(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    }

    return http.build();
  }
}
