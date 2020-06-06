package br.com.agateownz.foodsocial.config.security;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtCookieParser {

    @Autowired
    private JwtCookieConfig jwtCookieConfig;

    public Optional<String> getTokenFromRequest(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request.getCookies())) {
            return Optional.empty();
        }

        var authCookies = Arrays.stream(request.getCookies())
                .filter(c -> List.of(
                        jwtCookieConfig.getPayloadCookieName(),
                        jwtCookieConfig.getSignatureCookieName()).contains(c.getName()))
                .collect(Collectors.toList());

        if (authCookies.size() != 2) {
            return Optional.empty();
        }

        var maybePayload = authCookies.stream()
                .filter(c -> c.getName().equals(jwtCookieConfig.getPayloadCookieName()))
                .findFirst();

        var maybeSignature = authCookies.stream()
                .filter(c -> c.getName().equals(jwtCookieConfig.getSignatureCookieName()))
                .findFirst();

        var requestToken = Stream.of(maybePayload, maybeSignature)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Cookie::getValue)
                .collect(Collectors.joining("."));

        return Optional.of(requestToken);
    }
}
