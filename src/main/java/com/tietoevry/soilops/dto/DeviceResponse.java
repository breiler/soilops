package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DeviceResponse extends DeviceCreateRequest implements Serializable {
    private String uuid;
    private String placeUuid;
    private String key;
    private LocalDateTime created;
    private ObservationResponse latestObservation;
}
