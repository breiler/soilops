package com.tietoevry.soilops.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "A data type for defining the user credentials")
public class AuthenticationRequest implements Serializable {

    @ApiModelProperty(value = "The username of the user that will authenticate", example = "breiler", required = true)
    private String username;

    @ApiModelProperty(value = "The password for the user", example = "password", required = true)
    private String password;
}
