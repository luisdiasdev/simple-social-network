package br.com.agateownz.foodsocial.config.security;

import br.com.agateownz.foodsocial.modules.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenGenerator {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtCookieConfig jwtCookieConfig;

    public String generateForUser(User user) {
        var roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()),
                        SignatureAlgorithm.forName(jwtConfig.getSigningAlgorithm()))
                .setHeaderParam("typ", jwtConfig.getTokenType())
                .setIssuer(jwtConfig.getTokenIssuer())
                .setAudience(jwtConfig.getTokenAudience())
                .setSubject(user.getUsername())
                .setExpiration(this.getExpirationDate())
                .claim("rol", roles)
                .claim("userId", user.getId())
                .compact();
    }

    private Date getExpirationDate() {
        return Date.from(
                LocalDateTime.now()
                        .plusSeconds(jwtCookieConfig.getPayloadCookieDuration().toSeconds())
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }
}
