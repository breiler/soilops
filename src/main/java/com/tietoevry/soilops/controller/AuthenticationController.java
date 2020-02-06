package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.AuthenticationRequest;
import com.tietoevry.soilops.dto.JwtResponse;
import com.tietoevry.soilops.auth.jwt.JwtTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Authentication", produces = "application/json")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }


    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    @ApiOperation(value = "Authenticates a user", notes = "Authenticates and creates a token to be used for other REST operations.", response = JwtResponse.class)
    public ResponseEntity<?> createUserToken(
            @RequestBody
                    AuthenticationRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final String token = jwtTokenService.generateUserToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
