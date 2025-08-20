package br.com.agateownz.foodsocial.config.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static br.com.agateownz.foodsocial.config.security.SecurityConstants.AUTH_LOGIN_URL;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        var publicEndpoints = new String[]{
            AUTH_LOGIN_URL,
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
        };

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers(publicEndpoints).permitAll()
                .requestMatchers(HttpMethod.POST, "/users").anonymous()
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                    new JwtAuthenticationFilter(authenticationManager,
                    jwtCookieGenerator, jwtTokenGenerator), 
                UsernamePasswordAuthenticationFilter.class
            )
            .addFilterAfter(
                    new JwtAuthorizationFilter(authenticationManager,
                    jwtCookieParser, jwtTokenParser), 
                UsernamePasswordAuthenticationFilter.class
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
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