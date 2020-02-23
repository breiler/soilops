package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DeviceUpdateRequest implements Serializable {
    private String name;
    private String placeUuid;
}
