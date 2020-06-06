package br.com.agateownz.foodsocial.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
