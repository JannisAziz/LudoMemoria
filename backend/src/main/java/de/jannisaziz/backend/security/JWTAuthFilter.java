package de.jannisaziz.backend.security;

import de.jannisaziz.backend.user.User;
import de.jannisaziz.backend.user.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                    final EmailPasswordAuthToken authToken =
                            new EmailPasswordAuthToken(user.getUsername(), null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

        } catch (Exception e) {
            LOG.warn("Error while parsing token", e);
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

    public String getUser(String token) {
        if ((token != null) && !(token.equals("undefined")) && (!token.isBlank())) {
            try {
                return jwtUtilService.extractUsername(token);
            } catch (ExpiredJwtException e) {
                LOG.warn("Token expired");
                throw e;
            }
        }
        return null;
    }
}
