package br.com.agateownz.foodsocial.config.security;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtUserToken extends UsernamePasswordAuthenticationToken {

    private Long userId;

    public JwtUserToken(Object principal,
        Collection<? extends GrantedAuthority> authorities,
        Long userId) {
        super(principal, null, authorities);
        this.userId = userId;
    }
}
