package br.com.agateownz.foodsocial.config.security;

import java.util.Set;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtCookieGenerator {

    @Autowired
    private JwtCookieConfigurationProperties jwtCookieConfigurationProperties;

    public Set<Cookie> generateCookies(String accessToken, boolean isSecure) {
        var jwtParts = accessToken.split("\\.");
        if (jwtParts.length != 3) {
            return Set.of();
        }

        var signatureCookie = new Cookie(jwtCookieConfigurationProperties.getSignatureCookieName(), jwtParts[2]);
        signatureCookie.setHttpOnly(true);
        signatureCookie.setSecure(isSecure);
        signatureCookie.setPath("/");
        signatureCookie.setDomain(jwtCookieConfigurationProperties.getDomain());

        var payloadCookie = new Cookie(
            jwtCookieConfigurationProperties.getPayloadCookieName(),
            String.join(".", jwtParts[0], jwtParts[1]));
        payloadCookie.setMaxAge((int) jwtCookieConfigurationProperties.getPayloadCookieDuration().toMillis());
        payloadCookie.setHttpOnly(false);
        payloadCookie.setSecure(isSecure);
        payloadCookie.setDomain(jwtCookieConfigurationProperties.getDomain());
        payloadCookie.setPath("/");

        return Set.of(signatureCookie, payloadCookie);
    }
}
