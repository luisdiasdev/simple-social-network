package br.com.agateownz.foodsocial.config.security;

import br.com.agateownz.foodsocial.modules.user.model.User;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.agateownz.foodsocial.config.security.SecurityConstants.AUTH_LOGIN_URL;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtCookieGenerator jwtCookieGenerator;
    private final JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
        JwtCookieGenerator jwtCookieGenerator,
        JwtTokenGenerator jwtTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtCookieGenerator = jwtCookieGenerator;
        this.jwtTokenGenerator = jwtTokenGenerator;

        setFilterProcessesUrl(AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        var username = request.getParameter("username");
        var password = request.getParameter("password");
        var authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authentication) throws IOException, ServletException {

        var user = ((User) authentication.getPrincipal());

        var token = jwtTokenGenerator.generateForUser(user);

        jwtCookieGenerator.generateCookies(token, request.isSecure())
            .forEach(response::addCookie);
    }
}
