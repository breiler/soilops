package com.tietoevry.soilops.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = Logger.getLogger(RestAuthenticationEntryPoint.class.getSimpleName());

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        LOGGER.warning(String.format("Responding with unauthorized error. Message - %s", e.getMessage()));
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                e.getLocalizedMessage());
    }
}