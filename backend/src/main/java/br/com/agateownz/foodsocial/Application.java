package br.com.agateownz.foodsocial;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
        info = @Info(
                title = "food-social-api",
                version = "0.0.1",
                description = "The API for the Food Social Network",
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")))
@SecurityScheme(
        name = "cookieAuth",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.COOKIE,
        paramName = "payload")
@SecurityScheme(
        name = "cookieSignatureAuth",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.COOKIE,
        paramName = "signature")
@SpringBootApplication
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
