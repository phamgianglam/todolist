package com.example.todolist.filter;

import java.io.IOException;
import java.util.List;
import com.example.todolist.profile.Profile;
import com.example.todolist.profile.ProfileService;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.JwtUltil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUltil jwtUltil;

    @Autowired
    private ProfileService profileService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            username = jwtUltil.getUsername(jwt);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            List<Profile> profiles = this.profileService.getByUsername(username);

            if (profiles.isEmpty()){
                throw new Exceptions.ObjectNotFoundException("profile");
            }

            if (jwtUltil.isExpired(jwt)){
                // throw something
            } else {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(profiles.getFirst(), null, profiles.getFirst().getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
            }
        }
    }
}
