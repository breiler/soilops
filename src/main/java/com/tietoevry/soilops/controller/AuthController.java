package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.JwtResponse;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.security.jwt.TokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@Api(tags = "Authentication", value = "Handles the users authentication", produces = "application/json")
public class AuthController {

    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET)
    @ApiOperation(value = "Returns a token for the logged in user", response = JwtResponse.class)
    public ResponseEntity<JwtResponse> fetchToken(
            @ApiIgnore Principal principal) {
        String token = tokenProvider.createToken(principal);
        JwtResponse response = new JwtResponse(token);
        return ResponseEntity.ok(response);
    }
}
