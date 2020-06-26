package br.com.agateownz.foodsocial.config.security;

import java.time.Duration;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.security.jwt.cookie")
public class JwtCookieConfigurationProperties {

    private static final String DEFAULT_PAYLOAD_COOKIE_NAME = "payload";
    private static final String DEFAULT_SIGNATURE_COOKIE_NAME = "signature";
    private static final Duration DEFAULT_PAYLOAD_COOKIE_DURATION = Duration.ofHours(1);

    @NotBlank
    private String payloadCookieName = DEFAULT_PAYLOAD_COOKIE_NAME;

    @NotNull
    private Duration payloadCookieDuration = DEFAULT_PAYLOAD_COOKIE_DURATION;

    @NotBlank
    private String signatureCookieName = DEFAULT_SIGNATURE_COOKIE_NAME;

    @NotBlank
    private String domain;
}
