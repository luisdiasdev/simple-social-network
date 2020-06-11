package br.com.agateownz.foodsocial.config.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.security.jwt")
public class JwtConfig {

    private static final String DEFAULT_TOKEN_TYPE = "JWT";

    @NotNull
    @NotBlank
    private String secret;

    @NotNull
    @NotBlank
    private String signingAlgorithm;

    @NotNull
    @NotBlank
    private String tokenType = DEFAULT_TOKEN_TYPE;

    @NotNull
    @NotBlank
    private String tokenIssuer;

    @NotNull
    @NotBlank
    private String tokenAudience;
}
