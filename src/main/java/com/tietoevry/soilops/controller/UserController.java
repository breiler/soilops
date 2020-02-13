package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.UserResponse;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;


@RestController
@Api(tags = "Users", value = "Manages users", produces = "application/json")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the currently logged in user", response = UserResponse.class)
    public ResponseEntity<UserResponse> getUser(
            @ApiIgnore
                    Principal principal) {

        User user = userService.getUser(principal.getName());
        ModelMapper modelMapper = new ModelMapper();
        UserResponse result = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(result);
    }
}
