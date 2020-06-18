package br.com.agateownz.foodsocial.config.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static br.com.agateownz.foodsocial.config.security.SecurityConstants.AUTH_LOGIN_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtCookieGenerator jwtCookieGenerator;
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private JwtCookieParser jwtCookieParser;
    @Autowired
    private JwtTokenParser jwtTokenParser;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var publicEndpoints = new String[]{
            AUTH_LOGIN_URL,
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
        };

        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(publicEndpoints).permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtCookieGenerator, jwtTokenGenerator))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtCookieParser, jwtTokenParser))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE"));
        config.setAllowedHeaders(
            List.of("Accept", "Authorization", "Origin", "X-Requested-With", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
