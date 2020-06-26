package br.com.agateownz.foodsocial.config.security;

import br.com.agateownz.foodsocial.modules.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {

    @Autowired
    private JwtConfigurationProperties jwtConfigurationProperties;

    @Autowired
    private JwtCookieConfigurationProperties jwtCookieConfigurationProperties;

    public String generateForUser(User user) {
        var roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return Jwts.builder()
            .signWith(
                Keys.hmacShaKeyFor(jwtConfigurationProperties.getSecret().getBytes()),
                SignatureAlgorithm.forName(jwtConfigurationProperties.getSigningAlgorithm()))
            .setHeaderParam("typ", jwtConfigurationProperties.getTokenType())
            .setIssuer(jwtConfigurationProperties.getTokenIssuer())
            .setAudience(jwtConfigurationProperties.getTokenAudience())
            .setSubject(user.getUsername())
            .setExpiration(this.getExpirationDate())
            .claim("rol", roles)
            .claim("userId", user.getId())
            .compact();
    }

    private Date getExpirationDate() {
        return Date.from(
            LocalDateTime.now()
                .plusSeconds(jwtCookieConfigurationProperties.getPayloadCookieDuration().toSeconds())
                .atZone(ZoneId.systemDefault())
                .toInstant()
        );
    }
}
