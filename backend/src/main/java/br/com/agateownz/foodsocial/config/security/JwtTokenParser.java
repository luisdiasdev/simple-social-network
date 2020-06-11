package br.com.agateownz.foodsocial.config.security;

import io.jsonwebtoken.Jwts;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenParser {

    @Autowired
    private JwtConfig jwtConfig;

    public Optional<JwtUserToken> parseJwtToken(String token) {
        var parsedToken = Jwts.parser()
            .setSigningKey(jwtConfig.getSecret().getBytes())
            .parseClaimsJws(token);

        var username = parsedToken.getBody().getSubject();

        if (StringUtils.isEmpty(username)) {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        var roles = ((List<String>) parsedToken.getBody().get("rol", List.class))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        var userId = parsedToken.getBody().get("userId", Long.class);

        return Optional.of(new JwtUserToken(username, roles, userId));
    }
}
