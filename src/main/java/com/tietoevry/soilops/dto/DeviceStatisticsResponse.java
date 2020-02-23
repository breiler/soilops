package com.tietoevry.soilops.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeviceStatisticsResponse {
    @ApiModelProperty(value = "The unique id of the device")
    private String deviceUuid;

    List<DeviceStatusResponse> statusList;
}
