package com.taskmanager.app.config;


import com.taskmanager.app.component.JwtAuthenticationEntryPoint;
import com.taskmanager.app.service.JwtService;
import com.taskmanager.app.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthencationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
    private final JwtAuthenticationEntryPoint entryPoint; // inject
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        try {
            String username = jwtService.extractUserName(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.userDetailsService()
                        .loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    Long userId = jwtService.extractUserId(jwt);
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(userId);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.JwtException ex) {
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response,
                    new org.springframework.security.authentication.AuthenticationServiceException(
                            "JWT không hợp lệ hoặc đã hết hạn", ex));
        }

    }
}
