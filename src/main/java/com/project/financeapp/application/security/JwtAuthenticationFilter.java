package com.project.financeapp.application.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, java.io.IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("authHeader: {}", authHeader);
        try {
            String jwt = authHeader.substring(7);
            String email = jwtUtil.extractUsername(jwt);
            log.info("email: {}", email);

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                log.info("User already authenticated: {}", SecurityContextHolder.getContext().getAuthentication().getName());
                filterChain.doFilter(request, response);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Fetch user details without roles/authorities
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                log.info("userDetails: {}", userDetails);
                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    // Create authentication token without roles/authorities
                    log.info("token: {}", jwt);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails.getUsername(),
                                    null,
                                    null // No roles or authorities
                            );
                    log.info("authToken: {}", authToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
