package br.com.agateownz.foodsocial.modules.shared.annotations;

import br.com.agateownz.foodsocial.config.security.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({
    ObjectMapper.class,
    JwtConfigurationProperties.class,
    JwtCookieConfigurationProperties.class,
    JwtCookieGenerator.class,
    JwtTokenGenerator.class,
    JwtCookieParser.class,
    JwtTokenParser.class
})
@AutoConfigureMockMvc(printOnlyOnFailure = false, webDriverEnabled = false)
public @interface MockMvcContextConfiguration {

}
