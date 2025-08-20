package br.com.agateownz.foodsocial.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenParser {

    @Autowired
    private JwtConfigurationProperties jwtConfigurationProperties;

    public Optional<JwtUserToken> parseJwtToken(String token) {
        // Create SecretKey from the configuration secret
        SecretKey key = Keys.hmacShaKeyFor(
            jwtConfigurationProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
        
        // Parse the JWT token using the modern API
        Jws<Claims> parsed = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);

        String username = parsed.getPayload().getSubject();

        if (!StringUtils.hasLength(username)) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        List<String> rolesList = ((List<String>) parsed.getPayload().get("rol", List.class));
        List<SimpleGrantedAuthority> roles = rolesList != null
                ? rolesList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : List.of();

        var userId = parsed.getPayload().get("userId", Long.class);

        return Optional.of(new JwtUserToken(username, roles, userId));
    }
}
