package de.jannisaziz.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final static Log LOG = LogFactory.getLog(JWTAuthFilter.class);

    private final JWTUtilService jwtUtilService;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = extractToken(request);

        if (token != null) try {
            final String username = jwtUtilService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails user = userService.loadUserByUsername(username);

                if (jwtUtilService.validateToken(token, user.getUsername())) {
                    final UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

        } catch (Exception e) {
            LOG.warn("Error while parsing token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;
        else {
            return authHeader.replace("Bearer", "").trim();
        }
    }
}
