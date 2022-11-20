package ru.blog.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.blog.security.authentication.RefreshTokenAuthentication;
import ru.blog.security.utils.AuthorizationHeaderUtil;
import ru.blog.security.details.AccountUserDetails;
import ru.blog.security.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME_PARAMETER = "email";
    public static final String AUTHENTICATION_URL = "/auth/token";

    private final ObjectMapper objectMapper;

    private final JwtUtil jwtUtil;

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    public JwtTokenAuthenticationFilter(ObjectMapper objectMapper,
                                        JwtUtil jwtUtil,
                                        AuthorizationHeaderUtil authorizationHeaderUtil,
                                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.setFilterProcessesUrl(AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (hasRefreshToken(request)) {
            String refreshToken = authorizationHeaderUtil.getToken(request);
            RefreshTokenAuthentication refreshTokenAuthentication = new RefreshTokenAuthentication(refreshToken);
            return super.getAuthenticationManager().authenticate(refreshTokenAuthentication);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        writeTokens(request, response, (AccountUserDetails) authResult.getPrincipal());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void writeTokens(HttpServletRequest request, HttpServletResponse response, AccountUserDetails userDetails) throws IOException {
        response.setContentType("application/json");

        Map<String, String> tokenJson = jwtUtil.generateTokens(
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString());

        objectMapper.writeValue(response.getOutputStream(), tokenJson);
    }

    private boolean hasRefreshToken(HttpServletRequest request) {
        return authorizationHeaderUtil.hasAuthorizationToken(request);
    }
}

