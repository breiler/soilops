package com.tietoevry.soilops.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscoveryResponse {
    @ApiModelProperty(value = "The unique id of the device")
    private String deviceUuid;

    @ApiModelProperty(value = "The secret key to be used by the device for registering observations")
    private String key;

    @ApiModelProperty(value = "A one time pin code for binding the device to a user")
    private String pin;
}
