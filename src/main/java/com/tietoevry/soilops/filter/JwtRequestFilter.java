package com.tietoevry.soilops.filter;

import com.tietoevry.soilops.service.JwtTokenService;
import com.tietoevry.soilops.service.JwtUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenService jwtTokenService;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenService jwtTokenService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");

        Optional<Claims> claims = validateToken(tokenHeader);
        if (claims.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(claims.get());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }

    private Optional<Claims> validateToken(String tokenHeader) {
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the token
        if (StringUtils.isNotEmpty(tokenHeader)) {
            String token = tokenHeader;
            if (tokenHeader.startsWith("Bearer ")) {
                token = tokenHeader.substring(7);
            }

            try {
                return Optional.of(jwtTokenService.validateToken(token));
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        return Optional.empty();
    }
}