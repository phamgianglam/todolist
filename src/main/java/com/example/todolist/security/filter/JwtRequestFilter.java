package com.example.todolist.security.filter;

import com.example.todolist.model.Profile;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.JwtUltil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUltil jwtUltil;

  private final ProfileService profileService;

  public JwtRequestFilter(JwtUltil jwtUltil, ProfileService profileService) {
    this.jwtUltil = jwtUltil;
    this.profileService = profileService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;
    String role = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUltil.getUsername(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      List<Profile> profiles = this.profileService.getByUsername(username);

      if (profiles.isEmpty()) {
        throw new Exceptions.ObjectNotFoundException("profile");
      }

      if (jwtUltil.isExpired(jwt)) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
        return;
      }
      role = jwtUltil.getRole(jwt);
      List<GrantedAuthority> authorities =
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(username, null, authorities);

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }
}
