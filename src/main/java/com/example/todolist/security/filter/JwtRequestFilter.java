package com.example.todolist.security.filter;

import com.example.todolist.security.CustomUserDetail;
import com.example.todolist.security.CustomUserDetailService;
import com.example.todolist.util.JwtUltil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUltil jwtUltil;

  private CustomUserDetailService customUserDetailService;

  public JwtRequestFilter(JwtUltil jwtUltil, CustomUserDetailService customUserDetailService) {
    this.jwtUltil = jwtUltil;
    this.customUserDetailService = customUserDetailService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUltil.getUsername(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      CustomUserDetail customUserDetail = customUserDetailService.loadUserByUsername(username);

      if (!customUserDetail.isAccountNonExpired() || jwtUltil.isExpired(jwt)) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
        return;
      }

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(
              username, null, customUserDetail.getAuthorities());

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }
}
