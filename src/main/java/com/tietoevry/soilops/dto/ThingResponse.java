package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ThingResponse extends ThingRequest implements Serializable {
    private String uuid;
    private String placeUuid;
    private LocalDateTime created;
    private ObservationResponse latestObservation;
}
