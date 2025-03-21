package com.example.todolist.util;

import java.util.Date;
import com.example.todolist.profile.Profile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtUltil {
    private String secretKey = "abc";//replace this one with one come from config class
    private long validityInMilis = 36000;

    public String createToken(Profile profile) {
        Claims claims = Jwts.claims().setSubject(
                String.join(profile.getUsername()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilis);

        JwtBuilder builder = Jwts.builder().setClaims(claims).setExpiration(validity).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return  builder.compact();
    }

    public  Claims getClaim(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsername(String token){
        return getClaim(token).getSubject();
    }

    public boolean isExpired(String token){
        return getClaim(token).getExpiration().before(new Date());
    }

}
