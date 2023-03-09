package com.pi.kursy.security.configuration;

import com.pi.kursy.security.jwt.JwtToken;
import com.pi.kursy.security.jwt.JwtTokenFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenFactory tokenFactory;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            resolveJwt(request);
        } catch (Exception e) {
            log.warn("Could not set user authentication in security context due to unexpected error {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void resolveJwt(HttpServletRequest request) {
        var encodedJwt = getJwtFromRequest(request);
        if (encodedJwt == null) {
            return;
        }

        var jwt = tokenFactory.fromEncodedJwt(encodedJwt);
        if (invalidJwtToken(jwt)) {
            return;
        }

        var jwtData = jwt.getJwtData();
        var userDetails = userDetailsService.loadUserByUsername(jwtData.username());

        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return isValidBearerHeader(token) ? token.substring(7) : null;
    }

    private boolean isValidBearerHeader(String token) {
        // contains text and starts with value "Bearer "
        return StringUtils.hasText(token) && token.startsWith("Bearer ");
    }

    private boolean invalidJwtToken(JwtToken jwt) {
        if (!jwt.isValid()) {
            log.warn("jwt token is not valid");
            return true;
        }
        if (jwt.isExpired()) {
            log.warn("jwt token is expired");
            return true;
        }
        return false;
    }


}
