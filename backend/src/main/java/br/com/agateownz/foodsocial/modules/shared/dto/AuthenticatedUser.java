package br.com.agateownz.foodsocial.modules.shared.dto;

import br.com.agateownz.foodsocial.config.security.JwtUserToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class AuthenticatedUser {

    private Long userId;
    private String username;
    private Collection<GrantedAuthority> roles;

    public AuthenticatedUser(JwtUserToken authentication) {
        this.username = authentication.getName();
        this.userId = authentication.getUserId();
        this.roles = authentication.getAuthorities();
    }
}
