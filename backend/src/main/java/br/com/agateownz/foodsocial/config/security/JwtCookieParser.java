package br.com.agateownz.foodsocial.config.security;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieParser {

    @Autowired
    private JwtCookieConfigurationProperties jwtCookieConfigurationProperties;

    public Optional<String> getTokenFromRequest(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request.getCookies())) {
            return Optional.empty();
        }

        var authCookies = Arrays.stream(request.getCookies())
            .filter(c -> List.of(
                jwtCookieConfigurationProperties.getPayloadCookieName(),
                jwtCookieConfigurationProperties.getSignatureCookieName()).contains(c.getName()))
            .collect(Collectors.toList());

        if (authCookies.size() != 2) {
            return Optional.empty();
        }

        var maybePayload = authCookies.stream()
            .filter(c -> c.getName().equals(jwtCookieConfigurationProperties.getPayloadCookieName()))
            .findFirst();

        var maybeSignature = authCookies.stream()
            .filter(c -> c.getName().equals(jwtCookieConfigurationProperties.getSignatureCookieName()))
            .findFirst();

        var requestToken = Stream.of(maybePayload, maybeSignature)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Cookie::getValue)
            .collect(Collectors.joining("."));

        return Optional.of(requestToken);
    }
}
