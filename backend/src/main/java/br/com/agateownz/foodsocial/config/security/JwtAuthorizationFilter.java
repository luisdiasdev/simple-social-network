package br.com.agateownz.foodsocial.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtCookieParser jwtCookieParser;
    private final JwtTokenParser jwtTokenParser;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtCookieParser jwtCookieParser,
                                  JwtTokenParser jwtTokenParser) {
        super(authenticationManager);
        this.jwtCookieParser = jwtCookieParser;
        this.jwtTokenParser = jwtTokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        getAuthentication(request)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        filterChain.doFilter(request, response);
    }

    private Optional<JwtUserToken> getAuthentication(HttpServletRequest request) {
        var requestToken = jwtCookieParser.getTokenFromRequest(request);

        try {
            return requestToken
                    .map(jwtTokenParser::parseJwtToken)
                    .filter(Optional::isPresent)
                    .map(Optional::get);
        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT failed : {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT failed : {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT failed : {}", exception.getMessage());
        } catch (SignatureException exception) {
            log.warn("Request to parse JWT with invalid signature failed : {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT failed : {}", exception.getMessage());
        }

        return Optional.empty();
    }
}
