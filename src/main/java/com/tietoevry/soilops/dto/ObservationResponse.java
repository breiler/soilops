package com.tietoevry.soilops.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ObservationResponse {
    private String uuid;
    private String deviceUuid;
    private LocalDateTime created;
    private Double temperature;
    private Double moisture;
    private Double light;
}
