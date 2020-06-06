package br.com.agateownz.foodsocial.modules.shared.service;

import br.com.agateownz.foodsocial.config.security.JwtUserToken;
import br.com.agateownz.foodsocial.modules.shared.dto.AuthenticatedUser;
import br.com.agateownz.foodsocial.modules.shared.exception.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    public static Optional<JwtUserToken> getAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof JwtUserToken
                ? Optional.of((JwtUserToken) authentication)
                : Optional.empty();
    }

    public Optional<AuthenticatedUser> getAuthenticatedUser() {
        return getAuthentication()
                .map(AuthenticatedUser::new);
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedUser()
                .map(AuthenticatedUser::getUserId)
                .orElseThrow(UnauthorizedException::new);
    }
}
