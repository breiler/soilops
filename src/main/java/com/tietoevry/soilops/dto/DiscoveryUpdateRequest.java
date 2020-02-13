package com.tietoevry.soilops.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscoveryUpdateRequest {
    @ApiModelProperty(value = "A one time pin code for binding the device to a users", required = true)
    private String pin;

    @ApiModelProperty(value = "A optional name of the device", required = false)
    private String name;
}
