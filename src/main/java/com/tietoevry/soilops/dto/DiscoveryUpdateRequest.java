package com.tietoevry.soilops.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiscoveryUpdateRequest {
    @ApiModelProperty(required = true)
    private String placeUuid;

    /**
     * The pin code of the device to be registered
     */
    @ApiModelProperty(required = true)
    private String pin;

    @ApiModelProperty(required = false)
    private String name;
}
